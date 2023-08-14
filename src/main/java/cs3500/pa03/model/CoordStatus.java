package cs3500.pa03.model;

/**
 * Represents the status of a given coordinate on the Battleship board
 */
public enum CoordStatus {
  // green
  /**
   * coord has a ship there
   */
  SHIP("\u001B[32m" + "X" + "\u001B[0m"),
  // blue
  /**
   * coord has no ship there
   */
  NO_SHIP("\u001B[34m" + "O" + "\u001B[0m"),
  // red
  /**
   * coord has been hit there
   */
  HIT("\u001B[31m" + "H" + "\u001B[0m"),
  //yellow
  /**
   * coord had a shot that missed there
   */
  MISS("\u001B[33m" + "M" + "\u001B[0m");

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
