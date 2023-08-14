package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.Random;

/**
 * represents a coordinate for the BattleSalvo Game
 */
public class Coord {
  private final int posX;
  private final int posY;
  @JsonIgnore
  private CoordStatus coordStatus;

  /**
   * @param x position on a board
   * @param y position on a board
   */

  @JsonCreator
  public Coord(@JsonProperty("x") int x,
               @JsonProperty("y") int y) {
    this.posX = x;
    this.posY = y;
  }

  /**
   * @param x           position on a board
   * @param y           position on a board
   * @param coordStatus is the status of that coord
   */
  public Coord(int x, int y, CoordStatus coordStatus) {
    this.posX = x;
    this.posY = y;
    this.coordStatus = coordStatus;
  }

  /**
   * @return the x value of this coord
   */
  public int getX() {
    return posX;
  }

  /**
   * @return the y value of the coord
   */
  public int getY() {
    return posY;
  }

  /**
   *
   * @return the status of this coord
   */

  public CoordStatus getCoordStatus() {
    return coordStatus;
  }

  /**
   *
   * @param coordStatus is used to set the status of this coord
   */
  public void setCoordStatus(CoordStatus coordStatus) {
    this.coordStatus = coordStatus;
  }

  /**
   * @param widthBound  x value bound
   * @param heightBound y value bound
   * @return a random coord with the given bound
   */
  public static Coord randomCoord(int widthBound, int heightBound) {
    Random width = new Random();
    Random height = new Random();
    return new Coord(Math.abs(width.nextInt(widthBound)), Math.abs(height.nextInt(heightBound)));
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

}
