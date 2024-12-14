package servlet.util;

import java.util.*;

public class HashMapSort {
  public static HashMap<Integer, Double> sortByValue(HashMap<Integer, Double> hm) {
    List<Map.Entry<Integer, Double>> list =
        new LinkedList<Map.Entry<Integer, Double>>(hm.entrySet());
    Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
      public int compare(Map.Entry<Integer, Double> o1,
                         Map.Entry<Integer, Double> o2) {
        return (o1.getValue()).compareTo(o2.getValue());
      }
    });
    HashMap<Integer, Double> temp = new LinkedHashMap<Integer, Double>();
    for (Map.Entry<Integer, Double> aa : list) {
      temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
  }
}
