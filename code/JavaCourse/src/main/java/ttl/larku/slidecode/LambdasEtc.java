package ttl.larku.slidecode;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author whynot
 */
class LambdasEtc {
    public void fun() {
        Predicate<String> pred1 = new Predicate<>() {
            @Override
            public boolean test(String s) {
                return s.length() > 5;
            }
        };

        //vs
    }

    public void foo() {
        Predicate<String> pred2 = s -> {
            //lot's of code here
            return s.length() > 5;
        };

        //vs
        Predicate<String> pred3 = this::doAComplicatedTest;
    }

    public boolean doAComplicatedTest(String s) {
        //lot's of code here
        return s.length() > 5;
    }

    public void doit() {
        List<Integer> input = List.of(1, 20, 50, 33, 60404);
        List<Integer> result = filter(input, (s) -> s > 26);
        result.forEach(System.out::println);
    }

    public List<Integer> filter(List<Integer> input, Predicate<Integer> pred) {
        List<Integer> result = new ArrayList<>();
        for(Integer s : input) {
            if(pred.test(s)) {
                result.add(s);
            }
        }
        return result;
    }
}

@FunctionalInterface
interface MyInterface {
    public String x = "hello";
    public String doWork(String str, int num);

    public static String boo() {
        return "boo";
    }
}

class YYY {

    public void fun() {
        //For just a single statement, no curly braces required,
        //return statement NOT allowed.  The result of the expression
        //is the return value.
        MyInterface mi = (String s, int i) -> s.length() > i ? "Yes" : "No";
        //Arguments types can often be inferred by the compiler
        MyInterface mi2 = (s, i) -> s.length() > i ? "Yes" : "No";
        //For single argument functions, you can leave out the parentheses
        //around the argument.
        Consumer<String> cons = s -> System.out.println(s);
        //The above can be written like this.  Lot's of inferencing
        //and compiler help going on here.
        Consumer<String> cons2 = System.out::println;
        //For elaborate work, use curly braces, and a return
        //is REQUIRED (for non void functions)
        MyInterface mi3 = (s, i) -> {
            //other work
            return s.length() > i ? "Yes" : "No";
        };
    }
}
