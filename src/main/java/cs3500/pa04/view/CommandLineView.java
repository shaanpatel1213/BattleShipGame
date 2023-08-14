package cs3500.pa04.view;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.CoordStatus;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.ShipType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a CLI view
 */
public class CommandLineView implements View {

  Scanner scanner;

  /**
   * constructor uses a scanner for input
   *
   * @param scanner used to set up scanner field.
   */
  public CommandLineView(Scanner scanner) {
    //this.scanner = new Scanner(System.in);
    this.scanner = scanner;
  }

  @Override
  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * called to get the users name
   *
   * @return the name the user had entered
   */
  @Override
  public String getName() {
    System.out.println("""
        \n\nHello! Welcome to the OOD BattleSalvo Game! \n
        What is your name?""");
    String name = scanner.nextLine();
    return name;
  }

  @Override
  public Coord getValidDimensions() {
    System.out.println("""
       
        Please enter a valid height and width below:""");
    printDottedLine();

    int width;
    int height;
    // boolean displayError = false;

    do {
      width = scanner.nextInt();
      height = scanner.nextInt();
      if ((width < 6 || width > 15)
          || (height < 6 || height > 15)) {
        System.out.println("""
            \nUh Oh! You've entered invalid dimensions. Please remember that the
            height and width of the game must be in the range (6, 15), inclusive.
            Try again!""");
        printDottedLine();
      }
    } while ((width < 6 || width > 15)
        || (height < 6 || height > 15));
    return new Coord(width, height);
  }

  /**
   * Prints a dotted line
   */
  private void printDottedLine() {
    System.out.print("------------------------------------------------------\n");
  }

  @Override
  public Map<ShipType, Integer> getFleetSelection(int maxDimension) {
    System.out.printf("\nPlease enter your fleet in the order [Carrier (6), Battleship (5), "
        + "Destroyer (4), Submarine (3)].\n"
        + "Remember, your fleet may not exceed size %d.%n", maxDimension);
    printDottedLine();
    Map<ShipType, Integer> fleet = new HashMap<>();
    do {
      fleet.put(ShipType.CARRIER, scanner.nextInt());
      fleet.put(ShipType.BATTLESHIP, scanner.nextInt());
      fleet.put(ShipType.DESTROYER, scanner.nextInt());
      fleet.put(ShipType.SUBMARINE, scanner.nextInt());
      if (this.sum(fleet.values()) > maxDimension) {
        System.out.printf("""
            \nUh Oh! You've entered invalid fleet sizes.\n
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n
            Remember, your fleet may not exceed size %d.%n""", maxDimension);
        printDottedLine();
      }
      if (fleet.values().contains(0)) {
        System.out.printf("""
            \nUh Oh! You've entered invalid fleet sizes.\n
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n
            Remember, you must have at least 1 of each ship.%n""");
        printDottedLine();
      }
    } while (this.sum(fleet.values()) > maxDimension
        || fleet.values().contains(0));

    return fleet;
  }

  /**
   * @param values a list of values
   * @return the sum of values
   */
  private int sum(Collection<Integer> values) {
    int sum = 0;
    for (int value : values) {
      sum += value;
    }
    return sum;
  }

  @Override
  public void displayMyBoard(List<List<Coord>> board, String name) {
    StringBuilder boardDisplay = new StringBuilder();
    for (List<Coord> row : board) {
      boardDisplay.append("\t");
      for (Coord coord : row) {
        boardDisplay.append(coord.getCoordStatus().getStatusChar());
        boardDisplay.append(" ");
      }
      boardDisplay.append("\n");
    }
    System.out.printf("\nDisplaying %s's board%n", name);
    System.out.println("(X - your ships, M - opponent misses, H - opponent hits)");
    System.out.println(boardDisplay);
  }

  @Override
  public void displayOpponentBoard(List<List<Coord>> board, String name) {
    StringBuilder boardDisplay = new StringBuilder("");
    for (List<Coord> row : board) {
      boardDisplay.append("\t");
      for (Coord coord : row) {
        if (coord.getCoordStatus().getStatusChar()
            .equals(CoordStatus.SHIP.getStatusChar())) {
          boardDisplay.append(CoordStatus.WATER.getStatusChar());
        } else {
          boardDisplay.append(coord.getCoordStatus().getStatusChar());
        }
        boardDisplay.append(" ");
      }
      boardDisplay.append("\n");
    }
    System.out.printf("\nDisplaying %s's board%n", name);
    System.out.println("(M - your misses, H - your hits)");
    System.out.println(boardDisplay);
  }

  @Override
  public List<Coord> getShots(int numberOfShots, int boardWidth, int boardHeight) {
    List<Coord> shots = new ArrayList<>();
    do {
      System.out.printf("Please enter %d shots%n", numberOfShots);
      printDottedLine();
      for (int i = 0; i < numberOfShots; i++) {
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        shots.add(new Coord(y, x));
      }
    } while (!areValidShots(shots, boardWidth, boardHeight));
    return shots;
  }

  /**
   * @param shots       shots entered by the user
   * @param boardWidth  width of the board
   * @param boardHeight height of the board
   * @return whether the shots are valid
   */
  public boolean areValidShots(List<Coord> shots, int boardWidth, int boardHeight) {
    boolean toReturn = true;
    for (Coord coord : shots) {
      toReturn = toReturn
          && (coord.getY() < boardHeight
          && coord.getY() >= 0
          && coord.getX() < boardWidth
          && coord.getX() >= 0);
    }
    return toReturn;
  }

  @Override
  public void showGameEnd(GameResult result) {
    System.out.println("Game ended!");
  }

}