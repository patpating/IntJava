package ttl.larku.generics;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * An attempt to explain why the bounds are used in
 * signatures like Function<? super T, ? extends R>
 *
 * @author whynot
 */
public class GenericFunAndGames {

    public void go() {
        MyThing<Number> numberThing = new MyThing<>(22.3);
        MyThing<Integer> integerThing = new MyThing<>(22);

        //The types are the important things to keep track of
        //in this examples.  We say a function maps from the
        //first type (the type of the argument) to the second type
        //(the return type).  The names of the functions reflect
        //the mappings  e.g from Number to Number
        Function<Number, Number> numberToNumber = (n) -> 2.3;
        Function<Integer, Number> integerToNumber = (n) -> 2;

        Function<Number, Integer> numberToInteger = (n) -> 2;
        Function<Integer, Integer> integerToInteger = (n) -> 2;

        //This is straight ahead T's and R's, so it
        //would work without the ? super T or ? extends R
        MyThing<Number> nt1 = numberThing.map(numberToNumber);
        MyThing<Integer> it1 = integerThing.map(integerToInteger);

        //This one won't work without the ? super T.  T here is Integer,
        //because integerThing is MyThing<Integer>.  We are trying to
        //pass in a function which takes a Number, i.e. a ? super Integer
        //Which is a perfectly valid thing to do, but we are not allowed
        //to do it unless we tell the compiler it is okay with the
        //? super T
        MyThing<Integer> it2 = integerThing.map(numberToInteger);

        //This one won't work without the ? extends R
        //R is derived from whatever return type you are
        //trying to capture, so 'Number' in this case
        //Our function returns an Integer, i.e. a ? extends Number
        //We need to tell the compiler that is okay with ? extends R
        MyThing<Number> nt3 = numberThing.map(numberToInteger);
        //Same as above, but this one *will* work without
        //the ? extends R, because we are not capturing the
        //return, so there is no R for the compiler to check.
        numberThing.map(numberToInteger);
        //We could make it break by being explicit
        numberThing.<Number>map(numberToInteger);

        //Using method references

        MyThing<Number> nt4 = numberThing.map(this::fun2);
        MyThing<Number> nt5 = numberThing.map(this::fun2);

        //This next one will not compile.  Our T is Number and
        //we are trying to call a function which takes an Integer,
        // i.e. a ? extends Number.  Which is a no no no no no no.
        //MyThing<Number> ntBad = numberThing.map(this::fun3); //<-- will not compile


        MyThing<String> s = numberThing.map(this::fun4);
        MyThing<String> s2 = numberThing.map((n) -> "" + n.intValue());

        MyThing<Number> nn1 = new MyThing<>(Integer.valueOf(20));
        MyThing<Integer> nn2 = new MyThing<>(Integer.valueOf(20));

    }

    class MyThing<T> {
        private final T t;

        public MyThing(T t) {
            this.t = t;
        }
        public <R> MyThing<R> map(Function<? super T, ? extends R> mapper) {
            return new MyThing<R>(mapper.apply(t));
        }
    }


    public Integer fun3(Integer n) {
        return n.intValue() * n.intValue();
    }

    public Number funo(Object o) {
        return 10;
    }

    public Number fun(Number n) {
        return n.intValue() * n.intValue();
    }

    public Integer fun2(Number n) {
        return n.intValue() * n.intValue();
    }

    public String fun4(Number n) {
        return ("" + n.intValue() * n.intValue());
    }
}

//assume that A = return type, B and C are arguments
class Morefun<A, B, C> {
    B b;
    C c;

    Morefun(B b, C c) {
        this.b = b;
        this.c = c;
    }

    //Here the order of Type parameters is different.
    //Bad design!!
    A merge(BiFunction<? super B, ? super C,? extends A> remappingFunction) {
        return remappingFunction.apply(b, c);
    }

    //A different approach.  Here the Type Parameter A is ignored
    //And a brand new Parameter is used.  So this function can
    //potentially return a type different from A
    <R> R mergeToo(BiFunction<? super B, ? super C,? extends R> remappingFunction) {
        return remappingFunction.apply(b, c);
    }

    public static void main(String[] args) {
        Morefun<String, Integer, Double> mf = new Morefun<>(10, 20.2);

        BiFunction<Integer, Double, String> bf = (i, d) -> {
            return i.toString() + d;
        };

        BiFunction<Number, Number, String> bf2 = (i, d) -> {
            return i.toString() + d;
        };

        String result =  mf.merge(bf);
        System.out.println("result: " + result);
        String result2 =  mf.mergeToo(bf);
        System.out.println("result2: " + result2);

        String result3 =  mf.merge(Morefun::legacyMerge);

        //Double result4 = mf.merge(Morefun::onlyMergeToo);
        Double result4 = mf.mergeToo(Morefun::onlyMergeToo);
    }

    public static String legacyMerge(Number first, Number second) {
        return first.toString() + second;
    }

    public static Double onlyMergeToo(Number first, Number second) {
        return first.doubleValue() + second.doubleValue();
    }
}
