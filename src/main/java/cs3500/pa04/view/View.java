package cs3500.pa04.view;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ShipType;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents the view for this game.
 */
public interface View {
  /**
   * used to set the scanner
   *
   * @param scanner what the scanner field is set too
   */
  void setScanner(Scanner scanner);

  /**
   * called to get the users name
   *
   * @return the name the user had entered
   */
  String getName();

  /**
   *
   * @return dimensions between 6 and 15 stored as a Coord
   */
  Coord getValidDimensions();

  /**
   *
   * @param maxDimension the higher dimension of the game
   * @return the ship fleet selection
   */
  Map<ShipType, Integer> getFleetSelection(int maxDimension);

  /**
   * Displays the board for this player
   *
   * @param board the battleship board
   * @param name the name of the player
   */
  void displayMyBoard(List<List<Coord>> board, String name);

  /**
   * Displays the board for the opponent (hides ship location data)
   *
   * @param board the battleship board
   * @param name the name of the opponent
   */
  void displayOpponentBoard(List<List<Coord>> board, String name);

  /**
   *
   * @param numberOfShots number of shots to take
   * @param boardWidth the width of the board
   * @param boardHeight the height of the board
   * @return the coordinates of the shots
   */
  List<Coord> getShots(int numberOfShots, int boardWidth, int boardHeight);

  /**
   *
   * @param shots shots entered by the user
   * @param boardWidth width of the board
   * @param boardHeight height of the board
   * @return whether the shots are valid
   */
  boolean areValidShots(List<Coord> shots, int boardWidth, int boardHeight);

  /**
   *
   * @param result the result of the game
   */
  void showGameEnd(GameResult result);
}
