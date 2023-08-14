package cs3500.pa03.controller;

import java.util.Scanner;

/**
 * Handles the game start, running, and end.
 */
public interface Controller {
  /**
   * Method to handle running the game.
   */
  void run();

  /**
   *
   * @param scanner sets the scanner for this view.
   */
  void viewSetScanner(Scanner scanner);
}
