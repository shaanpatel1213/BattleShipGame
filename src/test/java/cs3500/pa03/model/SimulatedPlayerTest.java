package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.CommandLineView;
import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the SimulatedPlayer class.
 */
public class SimulatedPlayerTest {
  private AbstractPlayer player;
  private View view;

  /**
   * Sets up data for testing.
   */
  @BeforeEach
  public void setUp() {
    view = new CommandLineView(new Scanner(System.in));
    player = new SimulatedPlayer("AI Player", view);
    player.boardHeight = 6;
    player.boardWidth = 6;
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 2);
    player.specifications = specifications;

    List<Ship> shipPlacements = player.setupShipsPlacements();

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
  }

  /**
   * Tests the takeShots method.
   */
  @Test
  public void testTakeShots() {

    List<Coord> shots = player.takeShots();

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
