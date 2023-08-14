package cs3500.pa03.model;

import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents an abstract Player
 */
public abstract class AbstractPlayer implements Player {
  final String name;
  List<Ship> myShips;
  List<List<Coord>> myBoard;
  List<List<Coord>> theirBoard;
  final View view;
  int boardWidth;
  int boardHeight;
  Map<ShipType, Integer> specifications;

  /**
   * Default constructor
   *
   * @param name name of the player
   * @param view provides access to the game view
   */
  public AbstractPlayer(String name, View view) {
    this.name = name;
    this.view = view;
    this.myShips = new ArrayList<>();
  }

  /**
   * Sets the scanner.
   *
   * @param scanner scanner to set.
   */
  public void viewSetScanner(Scanner scanner) {
    view.setScanner(scanner);
  }

  /**
   * Initializes boards
   */
  public void initializeBoards() {
    this.myBoard = new ArrayList<>();
    this.theirBoard = new ArrayList<>();
    for (int i = 0; i < this.boardHeight; i++) {
      List<Coord> row = new ArrayList<>();
      for (int j = 0; j < this.boardWidth; j++) {
        row.add(new Coord(j, i));
      }
      this.theirBoard.add(row);
      this.myBoard.add(row);
    }
  }

  /**
   *
   * @return my board
   */
  public List<List<Coord>> getMyBoard() {
    return this.myBoard;
  }

  /**
   *
   * @return opponent's board
   */
  public List<List<Coord>> getOpponentBoard() {
    return this.theirBoard;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {

    this.boardHeight = height;
    this.boardWidth = width;
    this.specifications = specifications;

    return setupShipsPlacements();
  }

  /**
   *
   * @return list of Ships with their placement
   */
  public List<Ship> setupShipsPlacements() {
    this.initializeBoards();
    List<Ship> shipPlacements = new ArrayList<>();

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      ShipType shipType = entry.getKey();
      int shipCount = entry.getValue();

      for (int i = 0; i < shipCount; i++) {
        Ship ship = new Ship(shipType);
        placeShip(ship);
        shipPlacements.add(ship);
        myShips.add(ship);
        for (Coord coord : ship.getPositions()) {
          int x = coord.getX();
          int y = coord.getY();
          this.setMyBoardCoordinate(x, y, CoordStatus.SHIP);
        }
      }
    }

    return shipPlacements;
  }

  /**
   * Adds coordinates to the ship.
   *
   * @param ship Ship to place on the board
   */
  private void placeShip(Ship ship) {
    Random random = new Random();
    int x;
    int y;
    boolean isVertical = random.nextBoolean();

    do {
      isVertical = random.nextBoolean();

      if (isVertical) {
        x = random.nextInt(0, boardWidth);
        y = random.nextInt(0, boardHeight - ship.getSize() + 1);
      } else {
        x = random.nextInt(0, boardWidth - ship.getSize() + 1);
        y = random.nextInt(0, boardHeight);
      }
    } while (!isPlacementValid(ship, x, y, isVertical));

    for (int i = 0; i < ship.getSize(); i++) {
      int currentX = isVertical ? x : x + i;
      int currentY = isVertical ? y + i : y;
      Coord coord = new Coord(currentX, currentY, CoordStatus.SHIP);
      ship.addCoord(coord);
    }
  }

  /**
   *
   * @param ship the ship to place
   * @param x the x-position of the ship
   * @param y the y-position of the ship
   * @param isVertical whether the ship is vertical
   * @return is the placement valid?
   */
  public boolean isPlacementValid(Ship ship, int x, int y, boolean isVertical) {

    for (int i = 0; i < ship.getSize(); i++) {
      int currentX = isVertical ? x : x + i;
      int currentY = isVertical ? y + i : y;

      if (currentX >= boardWidth || currentY >= boardHeight
          || myBoard.get(currentY).get(currentX).getStatus().equals(CoordStatus.SHIP)) {
        return false; // Collision detected
      }
    }

    return true; // No collision
  }

  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> shotsThatHit = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      Coord boardCoord = this.myBoard.get(coord.getY()).get(coord.getX());
      // checks if positions are same
      if (boardCoord.equals(coord)) {
        // if there is a ship here
        if (boardCoord.getStatus().equals(CoordStatus.SHIP)) {
          shotsThatHit.add(coord);
          // updates Ship class
          setShipCoordinateAsHit(coord.getX(), coord.getY());
          // shows location as hit on my board
          this.setMyBoardCoordinate(coord.getX(), coord.getY(), CoordStatus.HIT);
          // checks if the shot missed
        } else if (boardCoord.getStatus().equals(CoordStatus.NO_SHIP)) {
          // shows location as miss on my board
          this.setMyBoardCoordinate(coord.getX(), coord.getY(), CoordStatus.MISS);
        }
      }
    }
    return shotsThatHit;
  }

  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      this.setOpponentBoardCoordinate(coord.getX(), coord.getY(), CoordStatus.HIT);
    }
  }

  /**
   *
   * @return are all the ships sunk?
   */
  public boolean areAllShipsSunk() {
    this.removeSunkShips();
    for (Ship ship : myShips) {
      if (!ship.isSunk()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Goes into the ship class and sets the ship's position
   *    with the given coordinate as hit
   *
   * @param x the x-position
   * @param y the y-position
   */
  public void setShipCoordinateAsHit(int x, int y) {
    for (Ship ship : myShips) {
      ship.setShipPositionHit(x, y);
    }
  }

  /**
   * Sets the given position on my board with the given status.
   *
   * @param x x-position
   * @param y y-position
   * @param status the status
   */
  public void setMyBoardCoordinate(int x, int y, CoordStatus status) {
    List<Coord> row = this.myBoard.get(y);
    row.set(x, new Coord(x, y, status));
    this.myBoard.set(y, row);
  }

  /**
   * Sets the given position on opponent's board with the given status.
   *
   * @param x x-position
   * @param y y-position
   * @param status the status
   */
  private void setOpponentBoardCoordinate(int x, int y, CoordStatus status) {
    List<Coord> row = this.theirBoard.get(y);
    row.set(x, new Coord(x, y, status));
    this.theirBoard.set(y, row);
  }

  @Override
  public void endGame(GameResult result, String reason) {
    return;
  }

  /**
   * Removes sunk ships from this list of ships
   */
  public void removeSunkShips() {
    List<Ship> newShips = new ArrayList<>();
    for (Ship ship : this.myShips) {
      if (ship.isSunk()) {
        view.printShipSunk(this.name);
        continue;
      }
      newShips.add(ship);
    }
    this.myShips = newShips;
  }
}
