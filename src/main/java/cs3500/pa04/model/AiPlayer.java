package cs3500.pa04.model;

import java.util.ArrayList;
import java.util.List;

/**
 * repreents an AI that player that plays along side a human or another AIPlayer
 */
public class AiPlayer extends AbstractPlayer {
  ArrayList<Coord> recommendedShots = new ArrayList<>();

  /**
   * creates a new AI player and sets its name to AI
   */
  public AiPlayer() {
    super("AI");
  }

  /**
   * @return list of Coords where my AI takes shots
   */
  @Override
  public List<Coord> takeShots() {
    //PrintToCli view = new PrintToCli();
    //view.printBoard(getTheirBoard(), getMyBoard());
    updateRecommendedShots();
    List<Coord> shots = new ArrayList<>();
    while (recommendedShots.size() > 0 && shots.size() != getShipsLeft()
        && getShotsRemaining() > 0) {
      Coord thisCoord = recommendedShots.remove(0);
      if (thisCoord.getCoordStatus().equals(CoordStatus.WATER) && !shots.contains(thisCoord)) {
        shots.add(thisCoord);
        subtractShotsRemaining(1);
      }
    }
    while (shots.size() != getShipsLeft() && getShotsRemaining() > 0) {
      Coord randCoord = Coord.randomCoord(getMyBoard().size(), getMyBoard().get(0).size());
      int x = randCoord.getX();
      int y = randCoord.getY();
      Coord thisCoord = getTheirBoard().get(x).get(y);
      if (thisCoord.getCoordStatus().equals(CoordStatus.WATER)
          && !shots.contains(thisCoord) && getShotsRemaining() > 0) {
        shots.add(thisCoord);
        subtractShotsRemaining(1);
      }
      //System.out.println("Remaining Shots : " + getShotsRemaining());
    }

    for (Coord coord : shots) {
      int x = coord.getX();
      int y = coord.getY();
      Coord coordAttacking = getTheirBoard().get(x).get(y);
      if (!coordAttacking.getCoordStatus().equals(CoordStatus.HIT)) {
        coordAttacking.setCoordStatus(CoordStatus.MISS);
      }
    }


    //System.out.println("AI shots taken: " + shots.size());

    return shots;
  }

  /**
   * my super secret algorithm that calculates the best shots to take based on
   * the current board state
   */
  private void updateRecommendedShots() {
    List<Coord> hits = shotsThatHit();
    for (Coord coord : hits) {
      int x = coord.getX();
      int y = coord.getY();
      Boolean isVertical = null;
      Coord up = null;
      Coord down = null;
      Coord left = null;
      Coord right = null;

      try {
        up = getTheirBoard().get(x).get(y + 1);
      } catch (IndexOutOfBoundsException e) {
        up = null;
      }

      try {
        down = getTheirBoard().get(x).get(y - 1);
      } catch (IndexOutOfBoundsException e) {
        down = null;
      }
      try {
        left = getTheirBoard().get(x - 1).get(y);
      } catch (IndexOutOfBoundsException e) {
        left = null;
      }

      try {
        right = getTheirBoard().get(x + 1).get(y);
      } catch (IndexOutOfBoundsException e) {
        right = null;
      }
      if (up != null && up.getCoordStatus().equals(CoordStatus.WATER)) {
        if (down != null && down.getCoordStatus().equals(CoordStatus.HIT)) {
          recommendedShots.add(0, up);
        } else {
          recommendedShots.add(up);
        }
      }
      if (down != null && down.getCoordStatus().equals(CoordStatus.WATER)) {
        if (up != null && up.getCoordStatus().equals(CoordStatus.HIT)) {
          recommendedShots.add(0, down);
        } else {
          recommendedShots.add(down);
        }
      }
      if (left != null && left.getCoordStatus().equals(CoordStatus.WATER)) {
        if (right != null && right.getCoordStatus().equals(CoordStatus.HIT)) {
          recommendedShots.add(0, left);
        } else {
          recommendedShots.add(left);
        }
      }
      if (right != null && right.getCoordStatus().equals(CoordStatus.WATER)) {
        if (left != null && left.getCoordStatus().equals(CoordStatus.HIT)) {
          recommendedShots.add(0, right);
        } else {
          recommendedShots.add(right);
        }
      }
    }

  }

  /**
   * @return a list of coords that have coord status hit on their board
   */
  private List<Coord> shotsThatHit() {
    List<Coord> hits = new ArrayList<>();
    for (List<Coord> row : getTheirBoard()) {
      for (Coord coord : row) {
        if (coord.getCoordStatus().equals(CoordStatus.HIT)) {
          hits.add(coord);
        }
      }
    }
    return hits;
  }
}



