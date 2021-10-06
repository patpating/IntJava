package ttl.larku.tryexceptions.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class ExceptionsWithOptional {

    private static Logger logger = LoggerFactory.getLogger(ExceptionsWithOptional.class);

    public static void main(String[] args) {
        List<String> fileNames = Arrays.asList(".gitignore", "doesNotExist", "pom.xml");
//        callFileTheOldWay(fileNames);
        callFileWrappedOptional(fileNames);
//        callFileWrapped(fileNames);
    }

    public static void callFileTheOldWay(List<String> fileNames) {
        try {
            List<String> firstLines = filesTheOldWay(fileNames);
            //List<Character> firstLines = filesWithLambdasTheWrongWay(fileNames);
            firstLines.stream().map(String::toUpperCase).forEach(ch -> {
                //put into DB
                System.out.println(ch);
            });
        } catch (IOException e) {
            //log exception
            logger.error(e.toString());
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

    public static List<String> filesWithLambdasTheWrongWay(List<String> fileNames) {
        List<String> firstLines = fileNames.stream().map(fileName -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = reader.readLine();
                return line;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(toList());

        return firstLines;
    }


    public static List<Optional<String>> filesWithLambdasWithOptional(List<String> fileNames) {

        List<Optional<String>> firstLines = fileNames.stream()
                .map(fileName -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        String line = reader.readLine();
                        return Optional.of(line);
                    } catch (IOException e) {
                        return Optional.<String>empty();
                        //throw new RuntimeException(e);
                    }
                }).collect(toList());

        return firstLines;
    }

    public static void callFileWrappedOptional(List<String> fileNames) {
        List<Optional<String>> result = filesWithLambdasWithOptional(fileNames);
        result.stream()
                .filter(Optional::isPresent)
                .map(op -> op.map(ch -> ch.toUpperCase()))
                .map(Optional::get)
                .forEach(ch -> {
                    //send to DB
                    System.out.println("line: " + ch);
                });

    }

    public static List<Wrapper> filesWithLambdasWithWrapper(List<String> fileNames) {

        List<Wrapper> firstLines = fileNames.stream()
                .map(fileName -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        String line = reader.readLine();
                        return new Wrapper((line));
                    } catch (IOException e) {
                        return new Wrapper(e);
                        //throw new RuntimeException(e);
                    }
                }).collect(toList());

        return firstLines;
    }

    public static void callFileWrappedTheHardWay(List<String> fileNames) {
        List<Wrapper> result = filesWithLambdasWithWrapper(fileNames);
        result.stream()
                .forEach(w -> {
                    if (w.line != null) {
                        //send to DB
                        w.line = w.line.toUpperCase();
                        System.out.println("w: " + w);
                    } else {
                        logger.error(w.exception.toString());
                    }
                });

    }

    public static void callFileWrapped(List<String> fileNames) {
        List<Wrapper> result = filesWithLambdasWithWrapper(fileNames);
        result.stream()
//                .filter(w -> w.line != null)
                .map(w -> w.map(String::toUpperCase))
                .forEach(w -> {
                    if (w.line != null) {
                        //send to DB
                        System.out.println("w: " + w);
                    } else {
                        logger.error(w.exception.toString());
                    }
                });

    }


    static class Wrapper {
        public String line;
        public Exception exception;



        private Wrapper(String line) {
            this.line = line;
        }

        private Wrapper(Exception e) {
            exception = e;
        }


        public static Wrapper of(String line) {
            return new Wrapper(line);
        }

        public Wrapper flatMap(Function<String, Wrapper> func) {
            Wrapper w = func.apply(line);
            return w;
        }

        public Wrapper map(Function<String, String> func) {
            if (line != null) {
                line = func.apply(line);
                return new Wrapper(line);
            }
            return this;
        }

        @Override
        public String toString() {
            return "Wrapper{" +
                    "line=" + line +
                    ", exception=" + exception +
                    '}';
        }
    }
}

