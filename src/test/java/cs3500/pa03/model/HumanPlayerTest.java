package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.CommandLineView;
import cs3500.pa03.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the HumanPlayer class.
 */
public class HumanPlayerTest {
  private AbstractPlayer player;
  private View view;
  private ByteArrayOutputStream outputStream;
  private InputStream inputStream;

  /**
   * Sets up data for testing.
   */
  @BeforeEach
  public void setUp() {
    view = new CommandLineView(new Scanner(System.in));
    player = new HumanPlayer("Human Player", view);
    player.boardHeight = 6;
    player.boardWidth = 6;
    Map<ShipType, Integer> specifications = new HashMap<>();

    player.specifications = specifications;

    player.initializeBoards();
    player.myShips = new ArrayList<>();
    Ship ship1 = new Ship(ShipType.SUBMARINE);
    ship1.addCoord(new Coord(0, 0));
    ship1.addCoord(new Coord(1, 0));
    ship1.addCoord(new Coord(2, 0));
    Ship ship2 = new Ship(ShipType.SUBMARINE);
    ship2.addCoord(new Coord(0, 1));
    ship2.addCoord(new Coord(1, 1));
    ship2.addCoord(new Coord(2, 1));
    player.myShips.add(ship1);
    player.myShips.add(ship2);

    view = new CommandLineView(new Scanner(System.in));
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    inputStream = System.in;
  }

  /**
   * Resets the default input/output.
   */
  @AfterEach
  public void tearDown() {
    System.setOut(System.out);
    System.setIn(inputStream);
  }

  /**
   * Tests the takeShots method.
   */
  @Test
  public void testTakeShots() {

    assertEquals(player.myShips.size(), 2);
    assertFalse(player.areAllShipsSunk());

    String input = "1 1 1 2\n";
    InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(mockInputStream);
    player.viewSetScanner(new Scanner(System.in));
    List<Coord> shots = player.takeShots();

    List<Coord> expectedShots = List.of(
        new Coord(1, 1),
        new Coord(1, 2)
    );

    assertEquals(expectedShots, shots);

    assertEquals(player.myShips.size(), shots.size());

    int boardWidth = player.boardWidth;
    int boardHeight = player.boardHeight;

    for (Coord shot : shots) {
      int x = shot.getX();
      int y = shot.getY();

      assertTrue(x >= 0 && x < boardWidth);
      assertTrue(y >= 0 && y < boardHeight);
    }

    player.myShips.get(0).setShipPositionHit(0, 0);
    player.myShips.get(0).setShipPositionHit(1, 0);
    player.myShips.get(0).setShipPositionHit(2, 0);

    player.myShips.get(1).setShipPositionHit(0, 1);
    player.myShips.get(1).setShipPositionHit(1, 1);
    player.myShips.get(1).setShipPositionHit(2, 1);

    boolean result = player.areAllShipsSunk();

    assertTrue(result);

    shots = player.takeShots();

    assertEquals(0, shots.size());
  }
}
