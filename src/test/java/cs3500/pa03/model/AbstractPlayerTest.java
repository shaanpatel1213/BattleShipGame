package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
 * Tests the AbstractPlayer class.
 */
public class AbstractPlayerTest {
  private AbstractPlayer player;
  private View mockView;

  /**
   * Sets up testing.
   */
  @BeforeEach
  public void setUp() {
    mockView = new CommandLineView(new Scanner(System.in));
    player = new HumanPlayer("Player 1", mockView);
  }

  /**
   * Tests the initializeBoards method.
   */
  @Test
  public void testInitializeBoards() {
    player.boardHeight = 6;
    player.boardWidth = 6;
    player.initializeBoards();

    List<List<Coord>> myBoard = player.getMyBoard();
    List<List<Coord>> opponentBoard = player.getOpponentBoard();

    assertEquals(6, myBoard.size());
    assertEquals(6, myBoard.get(0).size());

    assertEquals(6, opponentBoard.size());
    assertEquals(6, opponentBoard.get(0).size());
  }

  /**
   * Tests the setupShipPlacements method.
   */
  @Test
  public void testSetupShipsPlacements() {
    player.boardHeight = 6;
    player.boardWidth = 6;
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 2);
    player.specifications = specifications;

    List<Ship> shipPlacements = player.setupShipsPlacements();

    assertEquals(3, shipPlacements.size());

    List<List<Coord>> myBoard = player.getMyBoard();

    for (Ship ship : shipPlacements) {
      for (Coord coord : ship.getPositions()) {
        int x = coord.getX();
        int y = coord.getY();
        assertEquals(CoordStatus.SHIP, myBoard.get(y).get(x).getStatus());
      }
    }
  }

  /**
   * Tests the isPlacementValid method.
   */
  @Test
  public void testIsPlacementValid() {
    player.boardHeight = 6;
    player.boardWidth = 6;
    player.initializeBoards();

    Ship ship = new Ship(ShipType.BATTLESHIP);
    boolean result = player.isPlacementValid(ship, 1, 1, true);
    assertTrue(result);
    result = player.isPlacementValid(ship, 7, 7, true);
    assertFalse(result);
    result = player.isPlacementValid(ship, 5, 7, true);
    assertFalse(result);
  }

  /**
   * Tests the reportDamage method.
   */
  @Test
  public void testReportDamage() {
    player.boardHeight = 6;
    player.boardWidth = 6;
    player.initializeBoards();

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(1, 0);

    player.setMyBoardCoordinate(0, 0, CoordStatus.SHIP);
    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(coord1);
    opponentShots.add(coord2);

    List<Coord> shotsThatHit = player.reportDamage(opponentShots);

    assertEquals(1, shotsThatHit.size());
    assertTrue(shotsThatHit.contains(coord1));

    List<List<Coord>> myBoard = player.getMyBoard();
    assertEquals(CoordStatus.HIT, myBoard.get(0).get(0).getStatus());
    assertEquals(CoordStatus.MISS, myBoard.get(0).get(1).getStatus());
  }

  /**
   * Tests the successfulHits method.
   */
  @Test
  public void testSuccessfulHits() {
    player.boardHeight = 6;
    player.boardWidth = 6;
    player.initializeBoards();

    Coord coord1 = new Coord(0, 0);
    Coord coord2 = new Coord(1, 0);
    List<Coord> shotsThatHitOpponentShips = new ArrayList<>();
    shotsThatHitOpponentShips.add(coord1);
    shotsThatHitOpponentShips.add(coord2);

    player.successfulHits(shotsThatHitOpponentShips);

    List<List<Coord>> opponentBoard = player.getOpponentBoard();
    assertEquals(CoordStatus.HIT, opponentBoard.get(0).get(0).getStatus());
    assertEquals(CoordStatus.HIT, opponentBoard.get(0).get(1).getStatus());
  }

  /**
   * Tests the areAllShipsSunk method.
   */
  @Test
  public void testAreAllShipsSunk() {
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

    boolean result = player.areAllShipsSunk();

    assertFalse(result);

    player.myShips.get(0).setShipPositionHit(0, 0);
    player.myShips.get(0).setShipPositionHit(1, 0);
    player.myShips.get(0).setShipPositionHit(2, 0);

    result = player.areAllShipsSunk();

    assertFalse(result);

    player.myShips.get(0).setShipPositionHit(0, 1);
    player.myShips.get(0).setShipPositionHit(1, 1);
    player.myShips.get(0).setShipPositionHit(2, 1);

    result = player.areAllShipsSunk();

    assertTrue(result);
  }

  /**
   * Tests the setShipCoordinateAsHit method.
   */
  @Test
  public void testSetShipCoordinateAsHit() {
    player.myShips = new ArrayList<>();
    Ship ship = new Ship(ShipType.BATTLESHIP);
    player.myShips.add(ship);
    player.myShips.get(0).addCoord(new Coord(0, 0));
    player.setShipCoordinateAsHit(0, 0);

    assertEquals(ship.getPositions().get(0).getStatus().getStatusChar(),
        CoordStatus.HIT.getStatusChar());
  }

  /**
   * Tests the setMyBoardCoordinate method.
   */
  @Test
  public void testSetMyBoardCoordinate() {
    player.boardHeight = 6;
    player.boardWidth = 6;
    player.initializeBoards();

    player.setMyBoardCoordinate(0, 0, CoordStatus.SHIP);
    player.setMyBoardCoordinate(1, 0, CoordStatus.NO_SHIP);

    List<List<Coord>> myBoard = player.getMyBoard();

    assertEquals(CoordStatus.SHIP, myBoard.get(0).get(0).getStatus());
    assertEquals(CoordStatus.NO_SHIP, myBoard.get(0).get(1).getStatus());
  }

  /**
   * Tests the removeSunkShips method.
   */
  @Test
  public void testRemoveSunkShips() {
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
    assertEquals(2, player.myShips.size());

    ship1.setShipPositionHit(0, 0);
    ship1.setShipPositionHit(1, 0);
    ship1.setShipPositionHit(2, 0);

    player.removeSunkShips();

    assertEquals(1, player.myShips.size());
    assertTrue(player.myShips.contains(ship2));
  }
}
