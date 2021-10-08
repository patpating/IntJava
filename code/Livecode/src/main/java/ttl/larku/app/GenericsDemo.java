package ttl.larku.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author whynot
 */
public class GenericsDemo {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericsDemo that = (GenericsDemo) o;
        return i == that.i;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i);
    }

    private int i;

    public static void main(String[] args) {
        GenericsDemo gd = new GenericsDemo();
        //gd.typeErasure();
        gd.wildCards();
    }

    /**
     * Object                                            List<Object>
     * <p>
     * Number                                            List<Number>
     * <p>
     * Integer, Double, Float                                List<Integer>
     */
    public void wildCards() {
        List<Object> lobj = new ArrayList<>();

        Integer[] iarr = {0, 2, 585};
        List<Number> numList = new ArrayList<>();
        numList.add(10);
        numList.add(22.54553);
        numList.add(443.3838);

        add(numList, iarr);

        double result = sum(numList);
        System.out.println("result: " + result);

        List<Integer> intList = new ArrayList<>();
        intList.add(10);
        intList.add(20);
        intList.add(30);

        result = sum(intList);

        add(intList, iarr);
        intList.forEach(System.out::println);

        List<String> lstr = new ArrayList<>();
//        add(lstr, iarr);
//        sum(lstr);

        int f = frequency(numList, 22.3);
        f = frequency(intList, 22.3);
        f = frequency(lstr, "abc");

        copy(numList, intList);

//        copy(intList, numList);
    }

    //T == number
    public static <T> void copy(List<? super T> dest, List<? extends T> source) {

    }

    static int frequency(Collection<?> c, Object o) {

//        c.add("abc");
        int num = 0;
        for(Object obj : c) {
            if(obj.equals(o)) {
                num++;
            }
        }
        return num;
    }


    //PECS - Producer Extends, Consumer Super


    //Consumer Super. Because dest is consuming the data
    public <T> void add(List<? super T> dest, T... arr) {
//       Integer x = dest.get(0);
        for (T it : arr) {
            dest.add(it);
        }
    }

    public void addElements(List<? super Integer> dest, Integer[] arr) {
        for (Integer it : arr) {
            dest.add(it);
        }
    }

    //Producer extends.  List input is a producer of data
    public <T extends Number> double sum(List<T> input) {
//        input.add(new AtomicInteger(0));
//        input.add(333.333);

        double sum = 0;
        for (Number n : input) {
            sum += n.doubleValue();
        }
        return sum;
    }


//    public double sum(List<Integer> input) {
//        double sum = 0;
//        for(Number n : input) {
//            sum += n.doubleValue();
//        }
//        return sum;
//    }

    public void typeErasure() {
        List<String> lStr = new ArrayList<>();
        lStr.add("one");
//        lStr.add(1);

        List<String> badList = lStr;
//        badList.add(1);

        Iterator it = lStr.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            String str = (String) o;
            System.out.println(str);
        }

        for (String str : lStr) {
            System.out.println(str);
        }
    }

}
