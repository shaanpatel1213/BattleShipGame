package cs3500.pa03.model;

/**
 * Represents the type of a ship
 */
public enum ShipType {
  /**
   * ship that is of size 6 being used for our game of BattleSalvo;
   */
  CARRIER(6),
  /**
   * ship that is of size 5 being used for our game of BattleSalvo;
   */
  BATTLESHIP(5),
  /**
   * ship that is of size 4 being used for our game of BattleSalvo;
   */
  DESTROYER(4),
  /**
   * ship that is of size 3 being used for our game of BattleSalvo;
   */
  SUBMARINE(3);

  ShipType(int size) {
    this.size = size;
  }

  final int size;

  /**
   *
   * @return the size of this type of ship
   */
  public int getSize() {
    return this.size;
  }
}
