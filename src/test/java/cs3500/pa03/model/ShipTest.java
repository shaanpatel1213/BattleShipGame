package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Ship class.
 */
class ShipTest {
  Ship ship1;
  Ship ship2;

  /**
   * Sets up data for testing.
   */
  @BeforeEach
  void setup() {
    ship1 = new Ship(ShipType.BATTLESHIP);
    ship1.addCoord(new Coord(0, 0, CoordStatus.HIT));
    ship1.addCoord(new Coord(0, 1, CoordStatus.HIT));

    ship2 = new Ship(ShipType.BATTLESHIP);
    ship2.addCoord(new Coord(0, 0, CoordStatus.HIT));
    ship2.addCoord(new Coord(0, 1, CoordStatus.NO_SHIP));
  }

  /**
   * Tests the setShipPositionHit method.
   */
  @Test
  void testSetShipPositionHit() {
    ship2.setShipPositionHit(0, 1);
    assertTrue(ship2.isSunk());
  }

  /**
   * Tests the isSunk method.
   */
  @Test
  void testIsSunk() {
    assertTrue(ship1.isSunk());
    assertFalse(ship2.isSunk());
  }

  /**
   * Tests the addCoord method.
   */
  @Test
  void testAddCoord() {
    ship2.setShipPositionHit(0, 1);
    ship2.addCoord(new Coord(0, 2, CoordStatus.HIT));
    assertTrue(ship2.isSunk());
  }

  /**
   * Tests the getPositions method.
   */
  @Test
  void testGetPositions() {
    assertEquals(ship1.getPositions(), new ArrayList<>(Arrays.asList(
        new Coord(0, 0, CoordStatus.HIT),
        new Coord(0, 1, CoordStatus.HIT))));
  }

  /**
   * Tests the getSize method.
   */
  @Test
  void testGetSize() {
    assertEquals(ship1.getSize(), 5);
  }
}