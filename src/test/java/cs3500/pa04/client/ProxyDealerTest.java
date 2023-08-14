package cs3500.pa04.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import client.Mocket;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyDealerTest {

  private ByteArrayOutputStream testLog;
  private ProxyDealer dealer;

  @BeforeEach
  void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }
  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */

  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  //@Test
  void runningServer() throws IOException, InterruptedException {
    int gamesWon = 0;
    int gamesLost = 0;
    ArrayList<String> reasons = new ArrayList<>();
    for (int x = 0; x < 100; x++) {
      System.out.println("Starting game " + x + "....");
      Socket server = new Socket("0.0.0.0", 35001);
      Player player = new AiPlayer();
      ProxyDealer proxyDealer = new ProxyDealer(server, player);
      proxyDealer.run();
      if (proxyDealer.result.equals(GameResult.WIN)) {
        gamesWon++;
      } else {
        gamesLost++;
        reasons.add(proxyDealer.reason);
      }
      Thread.sleep(50);
    }
    System.out.println("Games Won " + gamesWon);
    System.out.println("Games Lost " + gamesLost);
    for (String reason : reasons) {
      System.out.println(reason);
    }
  }

  @Test
  void run() throws IOException {
    String joinTest = "{\n"
        + "\t\"method-name\": \"join\",\n"
        + "\t\"arguments\": {}\n"
        + "}";
    //testResponse(joinTest, MessageJson.class);
    String setupTest = """
        {
        "method-name": "setup",
        "arguments": {
        "width": 10,
        "height": 10,
        "fleet-spec": {
        "CARRIER": 2,
        "BATTLESHIP": 4,
        "DESTROYER": 1,
        "SUBMARINE": 3
        }
        }
        }""";
    //testResponse(setupTest, MessageJson.class);
    String takeShots = """
     {"method-name": "take-shots","arguments": {}}""";
    //testResponse(takeShots, MessageJson.class);
    String reportDamage = """
        {
        "method-name": "report-damage",
        "arguments": {
            "coordinates": [
                {"x": 0, "y": 1},
                {"x": 3, "y": 2}
            ]
        }
        }""";
    //testResponse(reportDamage, MessageJson.class);
    String successfulHits = """
        {
          "method-name": "successful-hits",
          "arguments": {
              "coordinates": [
                  {"x": 0, "y": 1},
                  {"x": 3, "y": 2}
              ]
          }
        }""";
    //testResponse(successfulHits, MessageJson.class);
    String endGame = """
        {
          "method-name": "end-game",
          "arguments": {
              "result": "WIN",
              "reason": "Player 1 sank all of Player 2's ships"
          }
        }""";
    //testResponse(endGame, MessageJson.class);
    String error = """
        {
          "method-name": "error",
          "arguments": {
          }
        }""";
    List<String> args = new ArrayList<>();
    args.add(joinTest);
    args.add(setupTest);
    args.add(takeShots);
    args.add(reportDamage);
    args.add(successfulHits);
    args.add(endGame);
    args.add(error);

    testResponse(args, MessageJson.class);


  }

  private <T> void testResponse(List<String> jsonNode,
                                @SuppressWarnings("SameParameterValue") Class<T> classRef) {
    Socket server = new Mocket(this.testLog, jsonNode);
    Player ai = new AiPlayer();
    try {
      this.dealer = new ProxyDealer(server, ai);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    dealer.run();
    System.out.println(logToString());
    responseToClass(classRef);

  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      System.out.println(jsonParser.toString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }


}