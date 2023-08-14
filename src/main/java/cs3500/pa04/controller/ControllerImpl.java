package cs3500.pa04.controller;

import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.ShipType;
import cs3500.pa04.view.View;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**s
 * Represents a controller implementation
 */
public class ControllerImpl implements Controller {

  private View view;
  private Player player1;
  private Player player2;

  /**
   * constuctor for a controller
   *
   * @param player1 First player
   * @param player2 Second player
   * @param view View implementation
   */
  public ControllerImpl(Player player1, Player player2, View view) {
    this.view = view;
    this.player1 = player1;
    this.player2 = player2;
  }

  @Override
  public void viewSetScanner(Scanner scanner) {
    view.setScanner(scanner);
  }

  @Override
  public void run() {
    Coord dimensions = view.getValidDimensions();

    int maxDimension = Math.max(dimensions.getX(), dimensions.getY());

    Map<ShipType, Integer> fleet = view.getFleetSelection(maxDimension);

    player1.setup(dimensions.getX(), dimensions.getY(), fleet);
    player2.setup(dimensions.getX(), dimensions.getY(), fleet);

    List<Coord> damageToHuman;
    List<Coord> damageToAi;
    List<Coord> aiShots;
    List<Coord> humanShots;

    do {
      aiShots = player2.takeShots();
      humanShots = player1.takeShots();
      damageToHuman = player1.reportDamage(aiShots);
      damageToAi = player2.reportDamage(humanShots);

      player1.successfulHits(damageToAi);
      player2.successfulHits(damageToHuman);

    } while (!humanShots.isEmpty() && !aiShots.isEmpty());
    if (humanShots.isEmpty() && aiShots.isEmpty()) {
      player1.endGame(GameResult.DRAW, "Game ended in draw");
      view.showGameEnd(GameResult.DRAW);
    } else if (humanShots.isEmpty()) {
      player1.endGame(GameResult.LOSE, "All ships sunk!");
      view.showGameEnd(GameResult.LOSE);
    } else {
      player1.endGame(GameResult.WIN, "Sunk opponent ships!");
      view.showGameEnd(GameResult.WIN);
    }
  }
}
