package ttl.larku.app;

import java.util.HashSet;
import java.util.Set;

/**
 * @author whynot
 */
public class HashMapStepper {

    public static void main(String[] args) {
        HashMapStepper hms = new HashMapStepper();
        hms.go();
    }

    public void go() {
        Set<Integer> si = new HashSet<>();
        int end = 16 * 12;
        for(int i = 0; i < end; i += 16) {
            si.add(i);
        }
//        si.add(16);
//        si.add(0);
//        si.add(32);
//        si.add(64);


        si.forEach(System.out::println);
    }
}
