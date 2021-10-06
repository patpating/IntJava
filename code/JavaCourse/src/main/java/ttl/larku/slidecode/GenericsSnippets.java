package ttl.larku.slidecode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author whynot
 */
public class GenericsSnippets {

    public void fun() {
        List<String> goodReference = new ArrayList<>();

        goodReference.add("one");
        //lstr.add(1);      //Compiler error when using Generic reference

        //Using a Generic type in it's
        // Raw form, i.e. without the Generic Type.
        //This will compile but the compiler will
        // shriek out a warning about Raw Types.
        // Bad Bad Bad, DON'T do this
        List badReference = goodReference;

        badReference.add(1);   //No compile error

        //ClassCastException here if you use the raw reference
        for (String s : goodReference) {
            System.out.println(s);
        }
    }

    public void callAdd() {
        List<Number> lnum = new ArrayList<>();
        lnum.add(10);
        lnum.add(22.4);
        lnum.add(-393);

        List<Integer> inum = new ArrayList<>();
        lnum.add(10);
        lnum.add(22);
        lnum.add(-393);

        Number[] narr = {0, 33.6, 18};

        Integer[] iarr = {0, 33, 18};

        add2(lnum, narr);
        add2(lnum, iarr);

        add2(inum, iarr);

        addSingle(lnum, narr[0]);
        addSingle(lnum, iarr[0]);
//        addSingle(inum, narr[0]);
        addSingle(inum, iarr[0]);

        addList(lnum, lnum);
        addList(inum, inum);
//        addList(inum, lnum);
        addList(lnum, inum);
    }

    public <T> void addList(List<T> input, List<? extends T> other) {
        for (T t : other) {
            input.add(t);
        }
    }

    public void takeArray(Number[] narr) {

    }

    public void add2(List<? super Integer> input, Number[] arr) {

    }

    public <T extends Number> double sum(List<T> nums) {
        double sum = 0;
        for(Number n : nums) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public <T> void add(List<T> input, T[] arr) {
        for (T t : arr) {
            input.add(t);
        }
    }

    public <T> void addSingle(List<T> input, T t) {
        input.add(t);
    }

}
