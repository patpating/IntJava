package ttl.larku.slidecode;

import java.util.ArrayList;
import java.util.List;

/**
 * Note In the code below the notation T <- R means that
 *  type R is assignable (is a subtype) of T.
 *  e.g. Number <- Integer, or Number[] <- Integer[]
 * @author whynot
 */
public class CoContraGenerics {

    public static void main(String[] args) {
        new CoContraGenerics().arrays();
    }

    /**
     * Arrays are Covariant in Java.
     */
    public void arrays() {
        //Ordinary references are covariant.
        Integer it = 10;
        Number n = it;
        //Arrays are covariant in Java.
        //This means that the type relationship
        //between Java arrays varies in the
        //same ("Co") direction as the
        //underlying types
        //Number <- Integer implies Number[] <- Integer[]
        Integer [] itarr = new Integer[10];
        itarr[0] = 10;
        //This is covariance at work.  You can assign
        //The sub type to a super type reference
        Number [] narr = itarr;

        //But this can lead to issues at run time
        narr[2] = 22.5;
        for(Number nn : itarr) {
            System.out.println(nn);
        }
    }

    /**
     * Generic types in Java are *invariant* by default.
     * To play with variance rules, you need to use
     * the ? extends X (for covariance) and
     * ? super X (for contravariance)
     *
     * Wildcards are most often used to declare argument types.
     */
    public void lists() {
        //Ordinary references are covariant.
        Integer it = 10;
        Number n = it;

        List<Integer> lint = new ArrayList<>();
        //This will not compile, because Generic
        //types are *invariant*, so the relationship
        //between Number and Integer does NOT transfer
        //to the Lists
//        List<Number> lnumBad = lint;

        //Just a boring old List<Number>
        List<Number> boringNum = new ArrayList<>();

        //To allow the covariant relationship
        //We create a List<? extends Number>.
        //In exchange, we give up the ability
        //to add anything into the List.
        List<? extends Number> lnum = boringNum;
        //This next assignment is covariance in action.
        //The underlying types are Number <- Integer.
        //With the wild card, we get List<? extends Number> <- List<Integer>.
        //So the List types are varying in the same ("co")
        //direction as the underlying types
        List<? extends Number> lnum2 = lint;
//        lnum.add(10);

        //Contravariance goes in the other direction.
        //In this case we loose the ability to assume
        //anything about the type of elements.

        //This is not allowed any more
//        List<? super Number> lSuperInt = lint;

        //This is standard Number <- Number
        List<? super Number> lSuperInt2 = boringNum;

        //A plain old List<Object>
        List<Object> lobj = new ArrayList<>();

        //Here is the Contravariance
        //We can assign a List<Object> to a List<? super Number>.
        //So our underlying types have the relationship Object <- Number.
        //But our lists have the relationship List<? super Number> <- List<Object>.
        //In other words, List<Object> is a *subtype* of List<? super Number>
        lSuperInt2 = lobj;

    }
}
