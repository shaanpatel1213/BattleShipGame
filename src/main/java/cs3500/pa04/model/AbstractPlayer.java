package cs3500.pa04.model;

import cs3500.pa04.json.Dir;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * abstract player is used to write similar methods for both type of player in BattleSalvo
 */
public abstract class AbstractPlayer implements Player {
  private final String name;
  private final List<List<Coord>> theirBoard = new ArrayList<>();
  private final List<List<Coord>> myBoard = new ArrayList<>();
  private List<Ship> myShips;
  private int height;
  private int width;
  private int shotsRemaining;
  private int shipsLeft;

  /**
   * creates the player just sets the name
   *
   * @param name name of the player
   */
  public AbstractPlayer(String name) {
    this.name = name;
  }

  /**
   * is used to get the board width
   *
   * @return the board width
   */
  protected int getBoardWidth() {
    return this.width;
  }

  /**
   * is used to get the board height
   *
   * @return the board height
   */
  protected int getBoardHeight() {
    return this.height;
  }



  /**
   *
   * @return the name of the player playing
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   *
   * @return the board of where you have taken shots
   */
  protected List<List<Coord>> getTheirBoard() {
    return theirBoard;
  }

  /**
   *
   * @return the board of your fleet and ships
   */
  protected List<List<Coord>> getMyBoard() {
    return myBoard;
  }

  /**
   *
   * @return the number of ships that have not sunk
   */
  protected int getShipsLeft() {
    return shipsLeft;
  }

  /**
   *
   * @return the number of viable shots left of the board
   */
  protected int getShotsRemaining() {
    return shotsRemaining;
  }

  /**
   *
   * @param takeaway takes away shots that available to take;
   */
  protected void subtractShotsRemaining(int takeaway) {
    this.shotsRemaining -= takeaway;
  }

  /**
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return list of ships that is used to check when the game is over and that no one is cheating
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.height = height;
    this.width = width;
    this.shotsRemaining = height * width;
    for (int x = 0; x < width; x++) {
      List<Coord> myBoardRow = new ArrayList<>();
      List<Coord> theirBoardRow = new ArrayList<>();
      for (int y = 0; y < height; y++) {
        myBoardRow.add(new Coord(x, y, CoordStatus.WATER));
        theirBoardRow.add(new Coord(x, y, CoordStatus.WATER));
      }
      myBoard.add(x, myBoardRow);
      theirBoard.add(x, theirBoardRow);
    }
    List<Ship> allTheShips = new ArrayList<>();
    for (ShipType shipType : ShipType.values()) {
      for (int x = 0; x < specifications.get(shipType); x++) {
        allTheShips.add(setupShip(shipType));
      }

    }
    myShips = allTheShips;
    this.shipsLeft = allTheShips.size();
    return allTheShips;
  }

  /**
   *
   * @param shipType the type of ship that we are setting up
   * @return a ship that is randomly placed but properly setup by checking no overlap of ships
   */
  private Ship setupShip(ShipType shipType) {
    boolean validShipPlacement = false;
    Dir direction = null;
    ArrayList<Coord> shipCoords = new ArrayList<>();
    while (!validShipPlacement) {
      Random helpRandomizeVerticalOrHorizontal = new Random();
      Coord randCoord = Coord.randomCoord(width, height);
      if (randCoord.getX() + shipType.length <= width
          && helpRandomizeVerticalOrHorizontal.nextBoolean()) {
        //ship is horizontal and fits on board
        direction = Dir.HORIZONTAL;
        for (int x = 0; x < shipType.length; x++) {
          Coord coordOnBoard = myBoard.get(randCoord.getX() + x).get(randCoord.getY());
          if (coordOnBoard.getCoordStatus().equals(CoordStatus.WATER)) {
            //check if there is a ship in the way
            shipCoords.add(coordOnBoard);
            validShipPlacement = true;
          } else {
            validShipPlacement = false;
            break;
          }
        }
      } else if (randCoord.getY() + shipType.length <= height) {
        //ship is vertical
        direction = Dir.VERTICAL;
        for (int x = 0; x < shipType.length; x++) {
          Coord coordOnBoard = myBoard.get(randCoord.getX()).get(randCoord.getY() + x);
          if (coordOnBoard.getCoordStatus().equals(CoordStatus.WATER)) {
            shipCoords.add(coordOnBoard);
            validShipPlacement = true;
          } else {
            validShipPlacement = false;
            break;
          }
        }
      }
      if (validShipPlacement) {
        for (Coord coord : shipCoords) {
          coord.setCoordStatus(CoordStatus.SHIP);
        }
      } else {
        shipCoords.clear();
      }
    }
    return new Ship(shipCoords, shipType, direction);
  }


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> damageDone = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      int x = coord.getX();
      int y = coord.getY();
      Coord coordOnMyBoard = myBoard.get(x).get(y);
      if (coordOnMyBoard.getCoordStatus().equals(CoordStatus.SHIP)
          || coordOnMyBoard.getCoordStatus().equals(CoordStatus.HIT)) {
        damageDone.add(coord);
        coordOnMyBoard.setCoordStatus(CoordStatus.HIT);
      } else {
        coordOnMyBoard.setCoordStatus(CoordStatus.MISS);
      }
    }
    updateShipsLeft();
    return damageDone;
  }

  /**
   * used to update a variable internally for the amount of ships left
   */
  protected void updateShipsLeft() {
    int shipsLeftHere = myShips.size();
    for (Ship ship : myShips) {
      if (ship.shipSunk()) {
        shipsLeftHere--;
      }
    }
    this.shipsLeft = shipsLeftHere;
    //System.out.println("Updating ships left...." + shipsLeftHere);
  }


  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord coord : shotsThatHitOpponentShips) {
      int x = coord.getX();
      int y = coord.getY();
      Coord hitCoord = theirBoard.get(x).get(y);
      hitCoord.setCoordStatus(CoordStatus.HIT);
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    //temporary
    System.out.println(result.toString() + "!!! " + reason);
  }

}
