
/**
 * Single threaded test class for Chessboard. Runs a set number of tries.
 * Produces a hashmap of results.
 * 
 * @author linanqiu
 * @file_name SimulationSingle.java
 */
public class SimulationSingle {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    int tries = 1000;
    int height = 8;
    int width = 8;
    boolean backtrack = false;
    boolean diagonal = false;

    Result result = new Result();

    Chessboard chessboard = new Chessboard(height, width, backtrack, diagonal);

    for (int i = 0; i < tries; i++) {
      chessboard.placeAnt(0, 0);
      chessboard.placeAnt(7, 7);
      int moveReturn;

      while ((moveReturn = chessboard.moveAnts()) == Chessboard.NOTHING) {
      }
      if (moveReturn == Chessboard.CROSS) {
        result.addCross(chessboard.getMoveCount());
      } else {
        result.addMeet(chessboard.getMoveCount());
      }
      chessboard.reset();
    }

    System.out.println(result);
    long time = System.currentTimeMillis() - start;
    System.out.println("Runtime: " + time + " ms");
  }
}
