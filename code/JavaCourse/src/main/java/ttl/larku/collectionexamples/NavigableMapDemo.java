package ttl.larku.collectionexamples;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author whynot
 */
public class NavigableMapDemo {

    public static void main(String args[]) {

        //Need to create an implementation that implementst NavigableMap
        NavigableMap<Integer, String> navigableMap = new TreeMap<>();
        NavigableMap<Integer, String> concNavMap = new ConcurrentSkipListMap<>();

        navigableMap.put(90, "Hot");
        navigableMap.put(70, "Balmy");
        navigableMap.put(50, "Need a Jacket");
        navigableMap.put(20, "Stay at home");

        System.out.println("Map is sorted : " + navigableMap);

        //lowerKey gives Key which is strictly less than given key
        System.out.println("lowerKey(70): Key strictly lower than 70: " + navigableMap.lowerKey(70));

        //floorKey gives Key which is less than or equal to given key
        System.out.println("floorKey(70): Key less than or equal to 70: " + navigableMap.floorKey(70));

        //higherKey gives Key which is strictly higher than given key
        System.out.println("higherKey(70): Key strictly higher than 70: " + navigableMap.higherKey(70));

        //ceilingKey gives Key which is greater than or equal to given key
        System.out.println("ceilingKey(70): Key equal to or higher than 70: " + navigableMap.ceilingKey(70));

        //Submaps
        //A headMap is a map with keys strictly less than (or equal to, depending on the
        // second argument) a given key.
        NavigableMap<Integer, String> headMap = navigableMap.headMap(70, false);
        System.out.println("headMap(70, false): headMap 70: Map with Keys strictly lower than 70: " + headMap);

        NavigableMap<Integer, String> headMap2 = navigableMap.headMap(70, true);
        System.out.println("headMap(70, true): Map with Keys equal to or lower than 70: " + headMap2);

        //A tailMap is a map with keys strictly less than (or equal to, depending on the
        // second argument) a give key
        NavigableMap<Integer, String> tailMap = navigableMap.tailMap(70, false);
        System.out.println("tailMap(70, false): Map with Keys strictly higher than 70: " + tailMap);

        NavigableMap<Integer, String> tailMap2 = navigableMap.tailMap(70, true);
        System.out.println("tailMap(70: true): Map with Keys higher than or equal to 70: " + tailMap2);

        //subMap creates a map from one key to the other
        NavigableMap<Integer, String> subMap = navigableMap.subMap(50, true, 70, false);
        System.out.println("subMap(50, true, 70, false): " + subMap);
    }
}
