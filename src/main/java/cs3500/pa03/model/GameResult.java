package cs3500.pa03.model;

/**
 * Represents the result of a game
 */
public enum GameResult {
  /**
   * the GameResult if you win the game
   */
  WIN("You win!"),
  /**
   * the GameResult if you lose the game
   */
  LOSS("You lose!"),
  /**
   * the GameResult if you tie the game
   */
  TIE("You tied!");

  final String message;

  GameResult(String message) {
    this.message = message;
  }

  /**
   *
   * @return the game result message
   */
  public String getMessage() {
    return this.message;
  }
}