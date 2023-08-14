package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.json.Dir;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ShipTest {

  Ship testShip;

  ArrayList<Coord> setupForTests(CoordStatus status, ShipType type, Dir direction) {
    ArrayList<Coord> coords = new ArrayList<>();
    if (direction.equals(Dir.HORIZONTAL)) {
      for (int x = 0;  x < type.length; x++) {
        coords.add(new Coord(x, 0, status));
      }
    } else {
      for (int x = 0;  x < type.length; x++) {
        coords.add(new Coord(0, x, status));
      }
    }
    this.testShip = new Ship(coords, type, direction);
    return coords;
  }


  @Test
  void shipSunk() {
    for (Dir direction : Dir.values()) {
      for (ShipType shipType : ShipType.values()) {
        setupForTests(CoordStatus.SHIP, shipType, direction);
        assertFalse(testShip.shipSunk());
      }
    }
    for (Dir direction : Dir.values()) {
      for (ShipType shipType : ShipType.values()) {
        setupForTests(CoordStatus.HIT, shipType, direction);
        assertTrue(testShip.shipSunk());
      }
    }
  }

  @Test
  void getlength() {
    for (CoordStatus coordStatus : CoordStatus.values()) {
      for (Dir direction : Dir.values()) {
        for (ShipType shipType : ShipType.values()) {
          setupForTests(coordStatus, shipType, direction);
          assertEquals(testShip.getlength(), shipType.length);
        }
      }
    }
  }

  @Test
  void getDirection() {
    for (CoordStatus coordStatus : CoordStatus.values()) {
      for (Dir direction : Dir.values()) {
        for (ShipType shipType : ShipType.values()) {
          setupForTests(coordStatus, shipType, direction);
          assertEquals(testShip.getDirection(), direction);
        }
      }
    }


  }

  @Test
  void getCoord() {
    for (CoordStatus coordStatus : CoordStatus.values()) {
      for (Dir direction : Dir.values()) {
        for (ShipType shipType : ShipType.values()) {
          ArrayList<Coord> coords = setupForTests(coordStatus, shipType, direction);
          assertEquals(testShip.getCoord(), coords.get(0));
        }
      }
    }

  }
}