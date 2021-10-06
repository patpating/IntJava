//package ttl.larku.tryexceptions;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import ttl.trywrap.TryWrap;
//
//import static java.util.stream.Collectors.toList;
//
///**
// * @author whynot
// */
//public class TryWrapExceptions {
//
//    private static Logger logger = LoggerFactory.getLogger(TryWrapExceptions.class);
//
//    public static void main(String[] args) {
//        List<String> fileNames = Arrays.asList(".gitignore", "doesNotExist", "pom.xml");
////       callFileTheOldWay(fileNames);
//        //callFilesWithWrappedExceptions(fileNames);
//    }
//
//    public static void callFileTheOldWay(List<String> fileNames) {
//        try {
//            //List<String> firstChars = filesTheOldWay(fileNames);
//            List<String> firstChars = filesWithLambdasTheWrongWay(fileNames);
//            firstChars.stream().map(String::toUpperCase).forEach(ch -> {
//                //put into DB
//                System.out.println(ch);
//            });
//        } catch (IOException e) {
//            //log exception
//            logger.error(e.toString());
//            System.out.println(e);
//        }
//    }
//
//    /**
//     * Here we use one standard way of reading a line at a time in a for loop.
//     * We can deal the the Exception by throwing it if we want.
//     * @param fileNames
//     * @return
//     * @throws IOException
//     */
//    public static List<String> filesTheOldWay(List<String> fileNames) throws IOException {
//        List<String> firstChars = new ArrayList<>();
//        for (String fileName : fileNames) {
//            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//                String ch = reader.readLine();
//                firstChars.add(ch);
//            }
//        }
//        return firstChars;
//    }
//
//    /**
//     * Here we are using Streams, and, therefore, lambdas.  One consequence of
//     * using Lambdas is that we *have* to catch the checked IOException.  If
//     * we want to throw an Exception back, it has to be a RuntimeException.
//     * Also, the compiler is not going to force us to declare IOException.
//     * And, further, the caller who may have been expecting an IOException is
//     * now going to have to deal with a RuntimeException.  Awfullness.
//     * @param fileNames
//     * @return
//     */
//    public static List<String> filesWithLambdasTheWrongWay(List<String> fileNames) throws IOException {
//        List<String> firstChars = fileNames.stream().map(fileName -> {
//            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//                String line = reader.readLine();
//                return line;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).collect(toList());
//
//        return firstChars;
//    }
//
//
//    /**
//     * Here we use a Monad (or container) to hold either the result or the Exception.
//     * We are using the home grown TryWrap class.
//     *
//     * @param fileNames
//     * @return  A list of TryWrap<String> objects.
//     */
//    public static List<TryWrap<String>> filesWithWrappedExceptions(List<String> fileNames) {
//        List<TryWrap<String>> firstChars = fileNames.stream()
//                .map(fileName -> {
//                    return TryWrap.of(() -> {
//                        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//                            String line = reader.readLine();
//                            return line;
//                        }
//                    });
//                })
//                .collect(toList());
//
//        return firstChars;
//    }
//
//    public static void callFilesWithWrappedExceptions(List<String> fileNames) {
//        List<TryWrap<String>> firstChars = filesWithWrappedExceptions(fileNames);
//
//        firstChars.stream()
//                .map(tw -> tw.map(String::toUpperCase))
//                .forEach(tw -> {
//                    tw.ifPresentOrElseConsume(firstLine -> {
//                                //send to DB
//                                System.out.println(firstLine);
//                            },
//                            (e) -> {
//                                logger.error(e.toString());
//                            });
//                });
//    }
//
//
//
//}
//
