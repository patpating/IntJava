package ttl.larku.app;

import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class ExceptionsLambdas {

    public static void main(String[] args) {
        List<String> fileNames = Arrays.asList(".gitignore", "doesNotExist", "pom.xml");
//        callFileTheOldWay(fileNames);
        //callFilesWithLambdasAndOptionals(fileNames);
        //callFilesWithLambdasAndWrapper(fileNames);
        callFilesWithLambdasAndVavrTry(fileNames);
    }

    public static void callFileTheOldWay(List<String> fileNames) {
        try {
//            List<String> firstLines = filesTheOldWay(fileNames);
            List<String> firstLines = filesWithStreamsTheBadWay(fileNames);
            firstLines.stream().map(String::toUpperCase).forEach(ch -> {
                //put into DB
                System.out.println(ch);
            });
        } catch (RuntimeException e) {
            //log exception
            System.out.println(e);
        }
    }

    public static List<String> filesTheOldWay(List<String> fileNames) throws IOException {
        List<String> firstLines = new ArrayList<>();
        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String ch = reader.readLine();
                firstLines.add(ch);
            }
        }
        return firstLines;
    }

    public static List<String> filesWithStreamsTheBadWay(List<String> fileNames) {
        List<String> firstLines = fileNames.stream()
                .map(fileName -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        String ch = reader.readLine();
                        return ch;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(toList());

        return firstLines;
    }

    public static List<Optional<String>> filesWithLambdasAndOptionals(List<String> fileNames) {
        var firstLines = fileNames.stream()
                .map(fileName -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        String ch = reader.readLine();
                        return Optional.of(ch);
                    } catch (Exception e) {
                        return Optional.<String>empty();
//                        throw new RuntimeException(e);
                    }
                }).collect(toList());

        return firstLines;
    }

    public static void callFilesWithLambdasAndOptionals(List<String> fileNames) {
//            List<String> firstLines = filesTheOldWay(fileNames);
        List<Optional<String>> firstLines = filesWithLambdasAndOptionals(fileNames);

        var x = firstLines.stream()
                .map(opt -> opt.map(String::toUpperCase))
                .filter(opt -> opt.isPresent())
                .map(opt -> opt.get())
                .collect(toList());

        var y = firstLines.stream()
                .map(opt -> opt.map(String::toUpperCase))
                .flatMap(opt -> opt.stream())
                .collect(toList());

        var z = firstLines.stream()
                .flatMap(opt -> opt.map(String::toUpperCase).stream())
                .collect(toList());

        z.forEach(System.out::println);

    }

    public static List<Wrapper> filesWithLambdasAndWrapper(List<String> fileNames) {
        var firstLines = fileNames.stream()
                .map(fileName -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        String ch = reader.readLine();
                        return Wrapper.ofValue(ch);
                    } catch (Exception e) {
                        return Wrapper.ofError(e);
//                        throw new RuntimeException(e);
                    }
                }).collect(toList());

        return firstLines;
    }

    public static void callFilesWithLambdasAndWrapper(List<String> fileNames) {
//            List<String> firstLines = filesTheOldWay(fileNames);
        List<Wrapper> firstLines = filesWithLambdasAndWrapper(fileNames);

        firstLines.stream()
                .forEach(wrapper -> {
                    if (wrapper.value != null) {
                        System.out.println("Got value: " + wrapper.value);
                    } else {
                        System.out.println("Got exception: " + wrapper.exception);
                    }
                });

        List<String> x = firstLines.stream()
                .filter(w -> w.value != null)
                .map(w -> w.value)
                .collect(toList());

        var y = firstLines.stream()
                .collect(Collectors.partitioningBy(w -> w.value != null));

        y.forEach((k, v) -> System.out.println(k + ": " + v));

    }

    public static List<Try<String>> filesWithLambdasAndVavrTry(List<String> fileNames) {
        var firstLines = fileNames.stream()
                .map(fileName -> Try.of(() -> {
                            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                                String ch = reader.readLine();
                                return ch;
                            }
                        })
                ).collect(toList());

        return firstLines;
    }

    public static void callFilesWithLambdasAndVavrTry(List<String> fileNames) {
//            List<String> firstLines = filesTheOldWay(fileNames);
        List<Try<String>> firstLines = filesWithLambdasAndVavrTry(fileNames);

        firstLines.stream()
                .map(tw -> tw.map(String::toUpperCase))
                .forEach(wrapper -> {
                            wrapper.onSuccess(s -> System.out.println("Got value: " + s))
                                    .onFailure(e -> System.out.println("Got exception: " + e));
                        });


        List<String> x = firstLines.stream()
                .filter(w -> w.isSuccess())
                .map(w -> w.get())
                .collect(toList());

        var y = firstLines.stream()
                .collect(Collectors.partitioningBy(w -> w.isSuccess()));

        y.forEach((k, v) -> System.out.println(k + ": " + v));

    }

    static class Wrapper {
        public final String value;
        public final Exception exception;

        private Wrapper(String value, Exception exception) {
            this.value = value;
            this.exception = exception;
        }

        public static Wrapper ofValue(String value) {
            return new Wrapper(value, null);
        }

        public static Wrapper ofError(Exception exception) {
            return new Wrapper(null, exception);
        }

        @Override
        public String toString() {
            return "Wrapper{" +
                    "value='" + value + '\'' +
                    ", exception=" + exception +
                    '}';
        }

    }
}
