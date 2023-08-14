package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import cs3500.pa04.json.EmptyJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.VolleyJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.GameType;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyDealer {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  public GameResult result = GameResult.DRAW;
  public String reason = "There was a error";

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if
   */
  public ProxyDealer(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }


  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      //this.server.isClosed();
      // Disconnected from server or parsing exception
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    //System.out.println(message);
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    JsonNode jsonArguments = null;
    MessageJson jsonMessage = null;
    switch (name) {
      case "join":
        jsonArguments = JsonUtils.serializeRecord(handleJoin());
        jsonMessage = new MessageJson("join", jsonArguments);
        break;
      case "setup":
        jsonArguments = JsonUtils.serializeRecord(handleSetup(arguments));
        jsonMessage = new MessageJson("setup", jsonArguments);
        //System.out.println(jsonMessage);
        break;
      case "take-shots":
        jsonArguments = JsonUtils.serializeRecord(handleTakeShots());
        jsonMessage = new MessageJson("take-shots", jsonArguments);
        break;
      case "report-damage":
        jsonArguments = JsonUtils.serializeRecord(handleReportDamage(arguments));
        jsonMessage = new MessageJson("report-damage", jsonArguments);
        handleReportDamage(arguments);
        break;
      case "successful-hits":
        jsonArguments = JsonUtils.serializeRecord(handleSuccessfulHits(arguments));
        jsonMessage = new MessageJson("successful-hits", jsonArguments);
        break;
      case "end-game":
        jsonArguments = JsonUtils.serializeRecord(handleEndGame(arguments));
        jsonMessage = new MessageJson("end-game", jsonArguments);
        break;
      default:
        System.out.println("SHOULD NEVER GET TO THIS BRANCH");
        jsonArguments = JsonUtils.serializeRecord(new EmptyJson());
        jsonMessage = new MessageJson("error", jsonArguments);

    }
    JsonNode jsonResponse = JsonUtils.serializeRecord(jsonMessage);
    this.out.println(jsonResponse);
  }

  private JoinJson handleJoin() {
    JoinJson response = new JoinJson("shaanpatel1213", GameType.SINGLE);
    return response;
  }

  private FleetJson handleSetup(JsonNode arguments) {
    Integer width = this.mapper.convertValue(arguments.get("width"), Integer.class);
    Integer height = this.mapper.convertValue(arguments.get("height"), Integer.class);
    MapType collectionType = this.mapper.getTypeFactory().constructMapType(Map.class,
        ShipType.class, Integer.class);
    Map<ShipType, Integer> fleet = this.mapper.convertValue(arguments.get("fleet-spec"),
        collectionType);
    List<Ship> ships = this.player.setup(height, width, fleet);
    FleetJson response = new FleetJson(ships);
    return response;
  }

  private VolleyJson handleTakeShots() {
    List<Coord> shots = player.takeShots();
    VolleyJson response = new VolleyJson(shots);
    return response;
  }

  private VolleyJson handleReportDamage(JsonNode arguments) {
    VolleyJson shots = this.mapper.convertValue(arguments, VolleyJson.class);
    CollectionType collectionType = mapper.getTypeFactory()
        .constructCollectionType(ArrayList.class, Coord.class);
    ArrayList<Coord> shotsTaken  = this.mapper.convertValue(shots.getShots(), collectionType);
    List<Coord> shotsThatHit = player.reportDamage(shotsTaken);
    VolleyJson response = new VolleyJson(shotsThatHit);
    return response;
  }

  private EmptyJson handleSuccessfulHits(JsonNode arguments) {
    VolleyJson shots = this.mapper.convertValue(arguments, VolleyJson.class);
    CollectionType collectionType = mapper.getTypeFactory()
        .constructCollectionType(ArrayList.class, Coord.class);
    ArrayList<Coord> shotsTaken  = this.mapper.convertValue(shots.getShots(), collectionType);
    player.successfulHits(shotsTaken);
    EmptyJson response = new EmptyJson();
    return response;

  }

  private EmptyJson handleEndGame(JsonNode arguments) {
    GameResult result = this.mapper.convertValue(arguments.get("result"), GameResult.class);
    String reason = this.mapper.convertValue(arguments.get("reason"), String.class);
    player.endGame(result, reason);
    this.result = result;
    this.reason = reason;
    EmptyJson response = new EmptyJson();
    return response;
  }

}
