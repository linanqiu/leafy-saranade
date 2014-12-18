import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Multithreaded simulation. Should cut runtime by around 20 to 40% for larger
 * runs.
 * 
 * @author linanqiu
 * @file_name SimulationMulti.java
 */
public class SimulationMulti {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    int threadCount = 8; // not optimized. machine specific.
    int triesPerThread = 125000;
    int height = 8;
    int width = 8;
    boolean backtrack = false;
    boolean diagonal = false;

    ExecutorService executor = Executors.newFixedThreadPool(threadCount);

    ArrayList<FutureTask<Result>> futureTasks = new ArrayList<FutureTask<Result>>();

    ArrayList<Result> results = new ArrayList<Result>();

    for (int i = 0; i < threadCount; i++) {
      Worker worker = new Worker(height, width, backtrack, diagonal,
          triesPerThread);
      FutureTask<Result> futureTask = new FutureTask<Result>(worker);
      futureTasks.add(futureTask);
      executor.execute(futureTask);
    }

    while (true) {
      try {
        boolean allCompleted = true;

        Iterator<FutureTask<Result>> iterator = futureTasks.iterator();
        while (iterator.hasNext()) {
          FutureTask<Result> futureTask = iterator.next();
          if (!futureTask.isDone()) {
            allCompleted = false;
          } else {
            iterator.remove();

            Result result = futureTask.get();
            results.add(result);
          }
        }

        if (allCompleted) {
          Result result = new Result(results);
          System.out.println(result);
          long time = System.currentTimeMillis() - start;
          System.out.println("Runtime: " + time + " ms");
          executor.shutdown();
          return;
        }

      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }
}
