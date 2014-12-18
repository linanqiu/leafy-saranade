import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Result collector
 * 
 * @author linanqiu
 * @file_name Result.java
 */
public class Result {

  private int meetCount;
  private int crossCount;

  TreeMap<Integer, Integer> meetDistribution;
  TreeMap<Integer, Integer> crossDistribution;

  /**
   * New result sheet
   */
  public Result() {
    meetCount = 0;
    crossCount = 0;
    meetDistribution = new TreeMap<Integer, Integer>();
    crossDistribution = new TreeMap<Integer, Integer>();
  }

  /**
   * Concatenate several result sheets
   * 
   * @param results
   *          array list of results
   */
  public Result(ArrayList<Result> results) {
    meetCount = 0;
    crossCount = 0;
    meetDistribution = new TreeMap<Integer, Integer>();
    crossDistribution = new TreeMap<Integer, Integer>();
    for (Result result : results) {
      meetCount += result.getMeetCount();
      crossCount += result.getCrossCount();
      for (Integer meetKey : result.getMeetDistribution().keySet()) {
        if (meetDistribution.containsKey(meetKey)) {
          meetDistribution.put(meetKey, meetDistribution.get(meetKey)
              + result.getMeetDistribution().get(meetKey));
        } else {
          meetDistribution.put(meetKey,
              result.getMeetDistribution().get(meetKey));
        }
      }
      for (Integer crossKey : result.getCrossDistribution().keySet()) {
        if (crossDistribution.containsKey(crossKey)) {
          crossDistribution.put(crossKey, crossDistribution.get(crossKey)
              + result.getCrossDistribution().get(crossKey));
        } else {
          crossDistribution.put(crossKey,
              result.getCrossDistribution().get(crossKey));
        }
      }
    }
  }

  /**
   * Add a meet result with certain number of moves
   * 
   * @param moves
   *          numver of moves simulator made
   */
  public void addMeet(int moves) {
    meetCount++;
    if (meetDistribution.containsKey(moves)) {
      meetDistribution.put(moves, meetDistribution.get(moves) + 1);
    } else {
      meetDistribution.put(moves, 1);
    }
  }

  /**
   * Add a cross result with certain number of moves
   * 
   * @param moves
   *          number of moves simulator made
   */
  public void addCross(int moves) {
    crossCount++;
    if (crossDistribution.containsKey(moves)) {
      crossDistribution.put(moves, crossDistribution.get(moves) + 1);
    } else {
      crossDistribution.put(moves, 1);
    }
  }

  public int getMeetCount() {
    return meetCount;
  }

  public int getCrossCount() {
    return crossCount;
  }

  public TreeMap<Integer, Integer> getMeetDistribution() {
    return meetDistribution;
  }

  public TreeMap<Integer, Integer> getCrossDistribution() {
    return crossDistribution;
  }

  public String toString() {
    String returnString = "Cross: " + crossCount + "\n";
    returnString += crossDistribution.toString() + "\n";
    returnString += "Meet: " + meetCount + "\n";
    returnString += meetDistribution.toString();
    return returnString;
  }

}
