package ttl.larku.app;

import java.util.function.Function;

/**
 * @author whynot
 */
public class Func {

    public static void main(String[] args) {
        Function<Integer, Integer> sq = i -> i * i;
        Function<Integer, Integer> add = i -> i + i;

        int x = sq.apply(10);
        x = add.apply(x);
        System.out.println("x: " + x);

        int y = add.compose(sq).apply(10);
        System.out.println("y: " + y);


        int j = add.apply(10);
        int k = sq.apply(j);

        System.out.println("k: " + k);

        int l = add.andThen(sq).apply(10);
        System.out.println("l: " + l);
    }
}



