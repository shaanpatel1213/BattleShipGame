package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.view.CommandLineView;
import cs3500.pa04.view.View;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class HumanPlayerTest {

  @Test
  void takeShots() {
    System.setIn(
        new ByteArrayInputStream("0 0\n 1 1\n 2 2 \n 3 3\n 4 4\n 5 5\n 6 6\n 7 7\n".getBytes()));
    View view = new CommandLineView(new Scanner(System.in));
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, 3);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 3);
    map.put(ShipType.SUBMARINE, 1);
    HumanPlayer human = new HumanPlayer("HumanPlayer", view);
    human.setup(10, 10, map);
    List<Coord> shotByHuman = human.takeShots();
    assertEquals(shotByHuman.size(), 8);
  }

  @Test
  void takeShots2() {
    System.setIn(
        new ByteArrayInputStream("0 0\n 1 1\n 2 2 \n 3 3\n 4 4\n 5 5\n 6 6\n 7 7\n".getBytes()));
    View view = new CommandLineView(new Scanner(System.in));
    Map<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, 3);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 3);
    map.put(ShipType.SUBMARINE, 1);
    HumanPlayer human = new HumanPlayer("HumanPlayer", view);
    human.setup(10, 10, map);
    List<Coord> shotByHuman = human.takeShots();
    assertEquals(shotByHuman.size(), 8);
  }
}