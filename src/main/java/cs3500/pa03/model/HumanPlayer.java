package cs3500.pa03.model;

import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a human player
 */
public class HumanPlayer extends AbstractPlayer {
  /**
   * represents a human that is playing the game
   *
   * @param name the name of the player
   * @param view the way the player wants to view the content
   */
  public HumanPlayer(String name, View view) {
    super(name, view);
  }

  @Override
  public List<Coord> takeShots() {
    view.displayMyBoard(this.getMyBoard(), this.name());
    if (this.areAllShipsSunk()) {
      return new ArrayList<>();
    }
    this.removeSunkShips();
    return view.getShots(myShips.size(), this.boardWidth, this.boardHeight);
  }
}
