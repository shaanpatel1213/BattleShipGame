package cs3500.pa04.model;

/**
 * a Coord can have one of 4 status : HIT, MISS, SHIP, WATER
 */
public enum CoordStatus {
  /**
   * the status of the coord means that there was a ship there, and it was hit
   */
  HIT("\u001B[31m" + "H" + "\u001B[0m"),
  /**
   * the status of the coord means that there was not a ship here and missile was shot here
   */
  MISS("\u001B[33m" + "M" + "\u001B[0m"),
  /**
   * the status of the coord means that there is a ship here and that it has not been currently shot
   */
  SHIP("\u001B[32m" + "X" + "\u001B[0m"),
  /**
   * the status of the coord means that there is no ship here and that no missile was shot here
   */
  WATER("\u001B[34m" + "O" + "\u001B[0m");

  final String statusChar;

  CoordStatus(String statusChar) {
    this.statusChar = statusChar;
  }

  /**
   *
   * @return the status char
   */
  public String getStatusChar() {
    return this.statusChar;
  }

}
