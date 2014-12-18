import java.util.HashMap;

/**
 * Represents a chessboard with two ants on it. Simulates movement on the
 * chessboard.
 * 
 * @author linanqiu
 * @file_name Chessboard.java
 */
public class Chessboard {

  public static final int EMPTY = 0;

  public static final int CROSS = 2;
  public static final int MEET = 1;
  public static final int NOTHING = 0;

  // private int[] board;
  // only used for debugging purposes. takes up memory.
  private int height;
  private int width;
  private int antCount;

  private int moveCount;

  private boolean backtrack;
  private boolean diagonal;

  private HashMap<Integer, Integer> antPositions;
  private HashMap<Integer, Integer> backtrackPositions;

  /**
   * Constructs a new chessboard of height by width.
   * 
   * @param height
   *          height of chessboard
   * @param width
   *          width of chessboard
   * @param backtrack
   *          allows backtrack
   * @param diagonal
   *          allows diagonal movement
   */
  public Chessboard(int height, int width, boolean backtrack, boolean diagonal) {
    // board = new int[height * width];
    this.height = height;
    this.width = width;
    this.backtrack = backtrack;
    this.diagonal = diagonal;

    // for (int i = 0; i < board.length; i++) {
    // board[i] = EMPTY;
    // }

    moveCount = 0;
    antCount = 0;
    antPositions = new HashMap<Integer, Integer>();
    backtrackPositions = new HashMap<Integer, Integer>();
  }

  /**
   * Resets chessboard and removes ants
   */
  public void reset() {
    antCount = 0;
    antPositions = new HashMap<Integer, Integer>();
    moveCount = 0;
    // for (int i = 0; i < board.length; i++) {
    // board[i] = EMPTY;
    // }
  }

  /**
   * Places an ant at position (x, y). This program doesn't support more than 2
   * ants yet, though you're free to add more than 2. Crossing and meeting
   * detection isn't supported for more than 2 ants, and the behavior isn't
   * documented.
   * 
   * @param x
   *          x coordinate of the ant
   * @param y
   *          y coordinate of the ant
   */
  public void placeAnt(int x, int y) {
    antCount++;
    int boardPosition = x + y * width;
    antPositions.put(antCount, boardPosition);
    backtrackPositions.put(antCount, boardPosition);
    // board[boardPosition] = antCount;
  }

  // public String toString() {
  // String boardString = "";
  // for (int i = 0; i < height; i++) {
  // String row = "";
  // for (int j = 0; j < width; j++) {
  // row = row + board[i * height + j] + "\t";
  // }
  // boardString = row + "\n" + boardString;
  // }
  // return boardString;
  // }

  /**
   * Moves both ants to a random next step and return result.
   * 
   * @returnReturns NOMOVES if ants can't move anymore (for example in the no
   *                backtracking situation, when it surrounds itself with its
   *                previous moves). Returns MEET if both ants meet at the same
   *                square. Returns CROSS if both ants crossed.
   */
  public int moveAnts() {
    moveCount++;

    HashMap<Integer, Integer> previousPositions = new HashMap<Integer, Integer>(
        antPositions);

    for (int ant : antPositions.keySet()) {
      int[] availableMoves = availableMoves(antPositions.get(ant),
          backtrackPositions.get(ant));
      if (availableMoves.length > 0) {
        int chosenMove = (int) (Math.random() * availableMoves.length);
        int chosenPosition = availableMoves[chosenMove];
        // board[antPositions.get(ant)] = EMPTY;
        // board[chosenPosition] = ant;
        antPositions.put(ant, chosenPosition);
        backtrackPositions.put(ant, previousPositions.get(ant));
      }
    }

    if (previousPositions.get(1) == antPositions.get(2)
        && previousPositions.get(2) == antPositions.get(1)) {
      return CROSS;
    }
    if (antPositions.get(1) == antPositions.get(2)) {
      return MEET;
    }

    return NOTHING;
  }

  /**
   * Enumerates all possible moves for an ant given the boolean conditions set
   * in the constructor
   * 
   * @param position
   *          current position of ant
   * @return
   */
  private int[] availableMoves(int position, int backtrackPosition) {
    int movesCount = 0;
    int[] movesBig = new int[8];

    int x = position % width;
    int y = position / width;

    for (int tempX = Math.max(x - 1, 0); tempX <= Math.min(x + 1, width - 1); tempX++) {

      for (int tempY = Math.max(y - 1, 0); tempY <= Math.min(y + 1, height - 1); tempY++) {
        int consideredPosition = tempY * width + tempX;
        if (consideredPosition != position) {
          if (!diagonal && Math.abs((x - tempX) * (y - tempY)) == 1) {
            // is diagonal
          } else {
            if (consideredPosition < width * height && consideredPosition >= 0) {
              if (backtrack) {
                movesBig[movesCount] = consideredPosition;
                movesCount++;
              } else {
                if (consideredPosition != backtrackPosition) {
                  movesBig[movesCount] = consideredPosition;
                  movesCount++;
                }
              }
            }
          }
        }
      }
    }

    int[] movesReturn = new int[movesCount];
    for (int i = 0; i < movesReturn.length; i++) {
      movesReturn[i] = movesBig[i];
    }

    return movesReturn;
  }

  /**
   * Get number of moves made
   * 
   * @return
   */
  public int getMoveCount() {
    return moveCount;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
}
