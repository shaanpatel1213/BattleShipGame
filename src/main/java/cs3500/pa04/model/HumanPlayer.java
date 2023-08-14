package cs3500.pa04.model;

import cs3500.pa04.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a human player
 */
public class HumanPlayer extends AbstractPlayer {

  private View view;

  /**
   * human player constructor
   *
   * @param name name of the player
   * @param view the way the player wants there output given
   */
  public HumanPlayer(String name, View view) {
    super(name);
    this.view = view;
  }

  @Override
  public List<Coord> takeShots() {
    view.displayMyBoard(this.getMyBoard(), this.name());
    view.displayOpponentBoard(this.getTheirBoard(), "Opponent");
    List<Coord> shots = view.getShots(this.getShipsLeft(),
        this.getBoardWidth(), this.getBoardHeight());
    for (Coord coord : shots) {
      int x = coord.getX();
      int y = coord.getY();
      Coord coordAttacking = getTheirBoard().get(x).get(y);
      if (!coordAttacking.getCoordStatus().equals(CoordStatus.HIT)) {
        coordAttacking.setCoordStatus(CoordStatus.MISS);
      }
    }
    return shots;

  }
}
