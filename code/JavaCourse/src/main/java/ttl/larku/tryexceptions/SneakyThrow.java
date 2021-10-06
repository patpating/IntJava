package ttl.larku.tryexceptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author whynot
 */
public class SneakyThrow {
    public static void main(String[] args) {
        lookMaACheckExceptionWithoutADeclaration();
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


    public void withLambdasWillWithWrapper(List<String> fileNames) {
        fileNames.stream()
                .map(funcWrapper(fileName -> {
                            FileInputStream fis = new FileInputStream(fileName);
                            int c = fis.read();
                            return fileName + " had " + c;
                        })
                )
                .collect(Collectors.toList());
    }

    /**
     * We create an Function interface that throws Exceptions
     *
     * @param <T>
     * @param <R>
     * @param <E>
     */
    public interface FunctionThatThrows<T, R, E extends Exception> {
        public R apply(T t) throws E;

    }

    /**
     * This is
     * @param t
     * @param <E>
     * @param <R>
     * @return
     * @throws E
     */
    @SuppressWarnings("unchecked")
    static <E extends Exception, R> R sneakyThrow(Exception t) throws E {
        throw (E) t;
    }

    public static void lookMaACheckExceptionWithoutADeclaration() {
        sneakyThrow(new IOException("Boo"));
    }

    /**
     * A method that takes a FunctionThatThrows, and calls it
     * inside a try block.  We catch all Exceptions and throw as
     * RuntimeExceptions.  Could make this fancier and catch certain
     * exceptions and deal with them here.
     * <p>
     * Not that we return a Function object from here.  That is what
     * allows us to call this method in a Lambda.
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
                return sneakyThrow(e);
            }
        };
    }
}
