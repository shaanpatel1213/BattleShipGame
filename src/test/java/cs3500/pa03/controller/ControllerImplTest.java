package cs3500.pa03.controller;

import cs3500.pa03.model.Player;
import cs3500.pa03.model.SimulatedPlayer;
import cs3500.pa03.view.CommandLineView;
import cs3500.pa03.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the controller.
 */
class ControllerImplTest {

  private ByteArrayOutputStream outputStream;
  private InputStream inputStream;

  /**
   * Sets up testing input/output.
   */
  @BeforeEach
  public void setUp() {
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    inputStream = System.in;
  }

  /**
   * Resets default input/output.
   */
  @AfterEach
  public void tearDown() {
    System.setOut(System.out);
    System.setIn(inputStream);
  }

  /**
   * Tests the run method.
   */
  @Test
  void testRun() {
    View view = new CommandLineView(new Scanner(System.in));
    Player player1 = new SimulatedPlayer("AI1", view);
    Player player2 = new SimulatedPlayer("AI2", view);
    Controller controller = new ControllerImpl(player1, player2, view);
    System.setIn(new ByteArrayInputStream(("""
         6 78 \n 6 6\n1 1 1\n1 1 1 1\n"
         "0 0\n0 1\n0 2\n0 3\n0 4\n0 5\n"
         "1 0\n1 1\n1 2\n1 3\n1 4\n1 5\n"
         "2 0\n2 1\n2 2\n2 3\n2 4\n2 5\n"
         "20 0\n20 1\n20 2\n20 3\n20 4\n20 5\n"
         "3 0\n3 1\n3 2\n3 3\n3 4\n3 5\n"
         "4 0\n4 1\n4 2\n4 3\n4 4\n4 5\n"
         "5 0\n5 1\n5 2\n5 3\n5 4\n5 5\n""").getBytes()));

    controller.viewSetScanner(new Scanner(System.in));
    controller.run();
  }
}