package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * an enumeration of all the different ship types and there corresponding size
 */
public enum ShipType {
  /**
   * ship is a battleship with size 5
   */
  @JsonProperty("BATTLESHIP")
  BATTLESHIP(5),
  /**
   * ship is a carrier with size 6
   */
  @JsonProperty("CARRIER")
  CARRIER(6),
  /**
   * the ship is a destroyer and is size 4
   */
  @JsonProperty("DESTROYER")
  DESTROYER(4),
  /**
   * the ship is a submarine and is size 3
   */
  @JsonProperty("SUBMARINE")
  SUBMARINE(3);
  int length;

  ShipType(int length) {
    this.length = length;
  }

}
