package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a ship on the Battleship board
 */
public class Ship {
  private final ShipType shipType;

  // all occupied coordinates
  private final List<Coord> positions;

  /**
   * constructor of a ship
   *
   * @param shipType the type of ship it is
   */
  public Ship(ShipType shipType) {
    this.shipType = shipType;
    this.positions = new ArrayList<>();
  }

  /**
   * Sets the given position as hit
   *
   * @param x the x-position
   * @param y the y-position
   */
  public void setShipPositionHit(int x, int y) {
    for (Coord coord : positions) {
      if (coord.getX() == x && coord.getY() == y) {
        coord.setStatus(CoordStatus.HIT);
      }
    }
  }

  /**
   *
   * @return is the whole ship sunk?
   */
  public boolean isSunk() {
    for (Coord coord : positions) {
      if (!coord.getStatus().equals(CoordStatus.HIT)) {
        return false;
      }
    }
    return true;
  }

  /**
   *
   * @param coord coordinate to add to the ship's positions
   */
  public void addCoord(Coord coord) {
    positions.add(coord);
  }

  /**
   *
   * @return this ship's positions
   */
  public List<Coord> getPositions() {
    return this.positions;
  }

  /**
   *
   * @return the size of the ship
   */
  public int getSize() {
    return this.shipType.getSize();
  }
}
