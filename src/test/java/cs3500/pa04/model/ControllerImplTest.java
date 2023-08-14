package cs3500.pa04.model;


import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ControllerImpl;
import cs3500.pa04.view.CommandLineView;
import cs3500.pa04.view.View;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ControllerImplTest {

  @Test
  void run() {
    View view = new CommandLineView(new Scanner(System.in));
    System.setIn(new ByteArrayInputStream(("6 78 \n 6 6\n1 1 1 0\n1 1 1 1\n"
        + "0 0\n0 1\n0 2\n0 3\n0 4\n0 5\n"
        + "1 0\n1 1\n1 2\n1 3\n1 4\n1 5\n"
        + "2 0\n2 1\n2 2\n2 3\n2 4\n2 5\n"
        + "20 0\n20 1\n20 2\n20 3\n20 4\n20 5\n"
        + "3 0\n3 1\n3 2\n3 3\n3 4\n3 5\n"
        + "4 0\n4 1\n4 2\n4 3\n4 4\n4 5\n"
        + "5 0\n5 1\n5 2\n5 3\n5 4\n5 5\n").getBytes()));
    Controller controller = new ControllerImpl(new AiPlayer(), new AiPlayer(), view);
    controller.viewSetScanner(new Scanner(System.in));
    controller.run();
  }
}
