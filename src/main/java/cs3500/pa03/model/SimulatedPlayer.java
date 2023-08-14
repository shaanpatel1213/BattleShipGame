package cs3500.pa03.model;

import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents an AI player
 */
public class SimulatedPlayer extends AbstractPlayer {

  List<Coord> takenShots;

  /**
   * represents a player that is playing the game
   *
   * @param name the name of that player
   * @param view the way they will be viewing the game
   */
  public SimulatedPlayer(String name, View view) {
    super(name, view);
    takenShots = new ArrayList<>();
  }

  @Override
  public List<Coord> takeShots() {
    view.displayOpponentBoard(this.getOpponentBoard(), this.name());
    //view.displayMyBoard(this.getMyBoard(), this.name);
    this.removeSunkShips();
    if (this.areAllShipsSunk() || this.takenShots.size() == this.boardHeight * this.boardWidth) {
      return new ArrayList<>();
    }
    Random rand = new Random();
    List<Coord> shots = new ArrayList<>();
    Coord shot;
    for (int i = 0; i < this.myShips.size(); i++) {
      do {
        int x = rand.nextInt(0, boardWidth);
        int y = rand.nextInt(0, boardHeight);
        shot = new Coord(x, y);
      } while (takenShots.contains(shot));
      takenShots.add(shot);
      shots.add(shot);
    }
    return shots;
  }
}
