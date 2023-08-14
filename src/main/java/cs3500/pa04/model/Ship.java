package cs3500.pa04.model;

import cs3500.pa04.json.Dir;
import java.util.ArrayList;

/**
 * represents a ship for the BattleSalvo Game
 */
public class Ship {
  private final ArrayList<Coord> coords;

  private ShipType shipType;
  private Dir direction;

  /**
   *
   * @param coords the coord for where the ship is
   * @param shipType the type of ship it is
   * @param direction the direction of the ship
   */
  public Ship(ArrayList<Coord> coords, ShipType shipType, Dir direction) {
    this.coords = coords;
    this.shipType = shipType;
    this.direction = direction;
  }

  /**
   * @return true if all the coord of this ship have status HIT
   */
  public boolean shipSunk() {
    boolean shipSunk = true;
    for (Coord coord : this.coords) {
      if (!(coord.getCoordStatus().equals(CoordStatus.HIT))) {
        shipSunk = false;
      }
    }
    return shipSunk;
  }



  /**
   * Gets this ship's size.
   *
   * @return this ship's size
   */
  public int getlength() {
    return this.coords.size();
  }

  /**
   * Gets this ship's direction.
   *
   * @return this ship's direction.
   */
  public Dir getDirection() {
    return this.direction;
  }

  /**
   * Gets this ship's starting coordinate.
   *
   * @return this ship's left and top most coordinate.
   */
  public Coord getCoord() {
    return this.coords.get(0);
  }

}
