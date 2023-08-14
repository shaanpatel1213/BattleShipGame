package cs3500.pa04.client;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ControllerImpl;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.Player;
import cs3500.pa04.view.CommandLineView;
import cs3500.pa04.view.View;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The Driver is responsible for connecting to the server
 * and then running an entire game with a player.
 */
public class Driver {
  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    View view = new CommandLineView(new Scanner(System.in));
    Player player = new AiPlayer();
    ProxyDealer proxyDealer = new ProxyDealer(server, player);
    proxyDealer.run();
    // TODO: implement
  }

  /**
   * The main entrypoint into the code as the Client. Given a host and port as parameters, the
   * client is run. If there is an issue with the client or connecting,
   * an error message will be printed.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      // TODO: implement
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      try {
        Driver.runClient(host, port);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      View view = new CommandLineView(new Scanner(System.in));
      String name = view.getName();
      Player human = new HumanPlayer(name, view);
      Player ai = new AiPlayer();
      Controller controller = new ControllerImpl(human, ai, view);
      controller.run();
    }
  }
}