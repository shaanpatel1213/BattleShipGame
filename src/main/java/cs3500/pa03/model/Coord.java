package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Represents a coordinate and its "state"
 */
public class Coord {
  int posX;
  int posY;
  CoordStatus status;

  /**
   * Defaults to no-ship status
   *
   * @param x the x-position
   * @param y the y-position
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int x, @JsonProperty("y") int y) {
    this.posX = x;
    this.posY = y;
    this.status = CoordStatus.NO_SHIP;
  }

  /**
   * Constructor to provide a status
   *
   * @param x the x-position
   * @param y the y-position
   * @param status the status
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int x, @JsonProperty("y") int y, CoordStatus status) {
    this.posX = x;
    this.posY = y;
    this.status = status;
  }

  /**
   *
   * @return the x-position
   */
  public int getX() {
    return this.posX;
  }

  /**
   *
   * @return the y-position
   */
  public int getY() {
    return this.posY;
  }

  /**
   *
   * @return the status
   */
  public CoordStatus getStatus() {
    return this.status;
  }

  /**
   *
   * @param status to set
   */
  public void setStatus(CoordStatus status) {
    this.status = status;
  }

  /**
   * Checks equality based on x and y position only
   *
   * @param o object to compare
   * @return whether this and the given object are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return posX == coord.posX && posY == coord.posY;
  }

  /**
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(posX, posY);
  }

  /**
   *
   * @return the string representation
   */
  @Override
  public String toString() {
    return "Coord{"
        + "x=" + posX
        + ", y=" + posY
        + '}';
  }
}
