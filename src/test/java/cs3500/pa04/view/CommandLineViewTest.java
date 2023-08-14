package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.AbstractPlayer;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.HumanPlayer;
import cs3500.pa04.model.ShipType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the CommandLineView class.
 */
class CommandLineViewTest {
  private View view;
  private ByteArrayOutputStream outputStream;
  private InputStream inputStream;
  private AbstractPlayer player;

  /**
   * Sets up data for testing.
   */
  @BeforeEach
  public void setUp() {
    view = new CommandLineView(new Scanner(System.in));
    outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    inputStream = System.in;
    player = new HumanPlayer("Human", view);
  }

  /**
   * Resets default input/output.
   */
  @AfterEach
  public void tearDown() {
    System.setOut(System.out);
    System.setIn(inputStream);
  }

  /**
   * Tests the getValidDimensions method.
   */
  @Test
  public void testGetValidDimensions() {
    String input = "5 5\n20 20\n5 20\n20 5\n10 20\n20 10\n5 10\n10 5\n10 10\n";
    InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(mockInputStream);
    view.setScanner(new Scanner(System.in));
    Coord dimensions = view.getValidDimensions();

    assertEquals(10, dimensions.getX());
    assertEquals(10, dimensions.getY());
  }

  /**
   * Tests the getFleetSelection method.
   */
  @Test
  public void testGetFleetSelection() {
    String input = "1 1 1 1\n";
    InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(mockInputStream);
    view.setScanner(new Scanner(System.in));

    Map<ShipType, Integer> expectedFleet = new HashMap<>();
    expectedFleet.put(ShipType.CARRIER, 1);
    expectedFleet.put(ShipType.BATTLESHIP, 1);
    expectedFleet.put(ShipType.DESTROYER, 1);
    expectedFleet.put(ShipType.SUBMARINE, 1);

    Map<ShipType, Integer> fleet = view.getFleetSelection(10);
    assertEquals(expectedFleet, fleet);
  }

  /**
   * Tests the getFleetSelection method, but gives invalid input.
   */
  //@Test
  public void testGetFleetSelectionInvalid() {
    String input = "0 1 1 1\n1 1 1 1\n";
    InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(mockInputStream);
    view.setScanner(new Scanner(System.in));
    Map<ShipType, Integer> expectedFleet = new HashMap<>();
    expectedFleet.put(ShipType.CARRIER, 1);
    expectedFleet.put(ShipType.BATTLESHIP, 1);
    expectedFleet.put(ShipType.DESTROYER, 1);
    expectedFleet.put(ShipType.SUBMARINE, 1);
    Map<ShipType, Integer> fleet = view.getFleetSelection(10);
    assertEquals(expectedFleet, fleet);
    String expectedOutput1 = "\nPlease enter your fleet in the order [Carrier (6), "
        + "Battleship (5), Destroyer (4), Submarine (3)].\nRemember, your fleet may not exceed "
        + "size 10.\r\n"
        + "------------------------------------------------------\r\n";

    String expectedOutput2 = """
            \nUh Oh! You've entered invalid fleet sizes.\n
            Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n
            Remember, you must have at least 1 of each ship.\r\n"""
        + "------------------------------------------------------\n";
    String expectedOutput = expectedOutput1 + expectedOutput2;
    assertEquals(expectedOutput, outputStream.toString());

  }
  /*
    /**
     * Tests the displayMyBoard method.
     */
  //@Test
  //  public void testDisplayMyBoard() {
  //    Map<ShipType, Integer> specifications =  new HashMap<ShipType, Integer>();
  //    specifications.put(ShipType.CARRIER, 1);
  //    List<Ship> board = player.setup(6, 6, specifications);
  //    player.initializeBoards();
  //    String expectedOutput1 = "\nDisplaying Player's board\n"
  //        + "(X - your ships, M - opponent misses, H - opponent hits)\n";
  //    String expectedOutput2 =
  //        "\tO O O O O O \n"
  //            + "\tO O O O O O \n"
  //            + "\tO O O O O O \n"
  //            + "\tO O O O O O \n"
  //            + "\tO O O O O O \n"
  //            + "\tO O O O O O \n\r\n";
  //    expectedOutput2 = expectedOutput2.replace("O", CoordStatus.NO_SHIP.getStatusChar());
  //    String expectedOutput = expectedOutput1 + expectedOutput2;
  //
  //    view.displayMyBoard(player.getMyBoard(), "Player");
  //
  //    assertEquals(expectedOutput, outputStream.toString());
  //  }
  /*
    /**
     * Tests the displayOpponentBoard method.
     */
  //  //@Test
  //  public void testDisplayOpponentBoard() {
  //    Map<ShipType, Integer> specifications =  new HashMap<ShipType, Integer>();
  //    specifications.put(ShipType.CARRIER, 1);
  //    List<Ship> board = player.setup(6, 6, specifications);
  //    String expectedOutput1 = "\r\nDisplaying Opponent's board\r\n";
  //    String expectedOutput2 =
  //        "(M - your misses, H - your hits)\n"
  //            + "\tO O O O O O \r\n"
  //            + "\tO O O O O O \r\n"
  //            + "\tO O O O O O \r\n"
  //            + "\tO O O O O O \r\n"
  //            + "\tO O O O O O \r\n"
  //            + "\tO O O O O O \n\r\n";
  //    expectedOutput2 = expectedOutput2.replace("O", CoordStatus.NO_SHIP.getStatusChar());
  //    String expectedOutput = expectedOutput1 + expectedOutput2;
  //    view.displayOpponentBoard(player.getOpponentBoard(), "Opponent");
  //
  //    assertEquals(expectedOutput, outputStream.toString());
  //  }

  /**
   * Tests the getShots method.
   */
  @Test
  public void testGetShots() {
    String input = "1 2\n3 4\n5 6\n";
    InputStream mockInputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(mockInputStream);
    view.setScanner(new Scanner(System.in));

    List<Coord> shots = view.getShots(3, 10, 10);

    List<Coord> expectedShots = List.of(
        new Coord(2, 1),
        new Coord(4, 3),
        new Coord(6, 5)
    );

    assertEquals(expectedShots, shots);
  }

  /**
   * Tests the showGameEnd method.
   */
  @Test
  public void testShowGameEnd() {
    String expectedOutput = "Game ended!\n";

    view.showGameEnd(GameResult.WIN);

    assertEquals(expectedOutput, outputStream.toString());
  }

  //  /**
  //   * Tests the printShipSunk method.
  //   */
  //  @Test
  //  public void testPrintShipSunk() {
  //    String expectedOutput = "One of Player's ships sunk!\n";
  //
  //    view.printShipSunk("Player");
  //
  //    assertEquals(expectedOutput, outputStream.toString());
  //  }

  /**
   * Tests the areValidShots method.
   */
  @Test
  public void testAreValidShots() {
    List<Coord> coords = new ArrayList<>(Arrays.asList(
        new Coord(-1, -1)
    ));
    boolean result = view.areValidShots(coords, 6, 6);
    assertFalse(result);

    coords = new ArrayList<>(Arrays.asList(
        new Coord(10, 10)
    ));
    result = view.areValidShots(coords, 6, 6);
    assertFalse(result);

    coords = new ArrayList<>(Arrays.asList(
        new Coord(-1, 3)
    ));
    result = view.areValidShots(coords, 6, 6);
    assertFalse(result);

    coords = new ArrayList<>(Arrays.asList(
        new Coord(10, 3)
    ));
    result = view.areValidShots(coords, 6, 6);
    assertFalse(result);

    coords = new ArrayList<>(Arrays.asList(
        new Coord(0, 0),
        new Coord(1, 1),
        new Coord(2, 2),
        new Coord(3, 3),
        new Coord(4, 4),
        new Coord(5, 5)
    ));
    result = view.areValidShots(coords, 6, 6);
    assertTrue(result);
  }
}
