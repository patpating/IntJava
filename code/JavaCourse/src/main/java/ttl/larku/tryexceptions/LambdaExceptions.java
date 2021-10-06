package ttl.larku.tryexceptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author whynot
 */
public class LambdaExceptions {

    public static void main(String[] args) {
        List<String> files = Arrays.asList("/tmp/testFile");

       List<String> result = new LambdaExceptions().withLambdasWithWithWrapper(files);
       result.forEach(System.out::println);
    }

    public List<String> withoutLambdas(List<String> fileNames) throws IOException {
        List<String> results = new ArrayList<>();
        for (String fileName : fileNames) {
            FileInputStream fis = new FileInputStream(fileName);
            int c = fis.read();

            results.add(fileName + " had " + c);
        }
        return results;
    }


    /*
    public void withLambdasWillNotCompile(List<String> fileNames) {
        fileNames.stream()
                .map(fileName -> {
                    FileInputStream fis = new FileInputStream(fileName);
                    int c = fis.read();
                    return fileName + " had " + c;
                })
                .collect(Collectors.toList());
    }
     */


    public List<String> withLambdasWithWithWrapper(List<String> fileNames) {
        List<String> result = fileNames.stream()
                .map(funcWrapper(fileName -> {
                        //URL url = new URL("xyz.com");
                        DriverManager.getConnection("xyz");
                        FileInputStream fis = new FileInputStream(fileName);
                        int c = fis.read();
                        return fileName + " had " + c;
                    })
                )
                .collect(Collectors.toList());
        return result;
    }

    /**
     * We create an Function interface that throws Exceptions
     * @param <T>
     * @param <R>
     */
    public interface FunctionThatThrows<T, R, E extends Exception > {
        public R apply(T t) throws E ;
    }

    /**
     * A method that takes a FunctionThatThrows, and calls it
     * inside a try block.  We catch all Exceptions and throw as
     * RuntimeExceptions.  Could make this fancier and catch certain
     * exceptions and deal with them here.
     *
     * Note that we return a Function object from here.  That is what
     *  allows us to call this method in a Lambda.
     *
     * @param thrower
     * @param <T>
     * @param <R>
     * @param <E>
     * @return
     */
    public <T, R, E extends Exception> Function<T, R>
                            funcWrapper(FunctionThatThrows<T, R, E> thrower) {
        return (input) -> {
            try {
                return thrower.apply(input);
            } catch (Exception e) {
                if(e instanceof MalformedURLException) {
                    System.out.println("Handling MalFormedURL: " + e);
                }
                else if(e instanceof IOException) {
                    System.out.println("Handling IOException: " + e);
                }
                else {
                    throw new RuntimeException(e);
                }
                //bizarre, but necessary to satisfy compiler
                return null;
            }
        };
    }
}
