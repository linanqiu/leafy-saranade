import java.util.concurrent.Callable;

/**
 * Callable worker that allows multithreading of the chessboard
 * 
 * @author linanqiu
 * @file_name Worker.java
 */
public class Worker implements Callable<Result> {

  private Chessboard chessboard;
  private int tries;

  /**
   * Constructs a new worker holding a chessboard. What a cute image.
   * 
   * @param height
   *          height of the chessboard
   * @param width
   *          width of the chessboard
   * @param backtrack
   *          allow backtrack
   * @param diagonal
   *          allow diagonal movement
   * @param tries
   *          number of tries this single thread should run
   */
  public Worker(int height, int width, boolean backtrack, boolean diagonal,
      int tries) {
    this.chessboard = new Chessboard(height, width, backtrack, diagonal);
    this.tries = tries;
  }

  @Override
  public Result call() throws Exception {
    Result result = new Result();

    for (int i = 0; i < tries; i++) {
      chessboard.placeAnt(0, 0);
      chessboard.placeAnt(chessboard.getHeight() - 1, chessboard.getWidth() - 1);
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

    return result;
  }

}
