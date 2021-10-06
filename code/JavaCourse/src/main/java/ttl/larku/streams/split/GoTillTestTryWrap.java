//package ttl.larku.streams.split;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import ttl.trywrap.TryWrap;
//
//import java.io.FileReader;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static java.util.stream.Collectors.toList;
//import static ttl.larku.streams.split.GoTill.makeGoTill;
//
///**
// * @author whynot
// */
//public class GoTillTestTryWrap {
//    private static Logger logger = LoggerFactory.getLogger(GoTillTestTryWrap.class);
//
//    public static void main(String[] args) {
//        List<String> fileNames = Arrays.asList(".gitignore", ".classpath", "doesNotExist", "pom.xml");
////       callFileTheOldWay(fileNames);
//        callFilesWithWrappedExceptions(fileNames);
//    }
//
//    public static void callFilesWithWrappedExceptions(List<String> fileNames) {
//        List<TryWrap<Character>> firstChars = filesOnlyTillFirstException(fileNames);
//        firstChars.forEach(tw -> {
//            tw.ifPresentOrElseConsume(ch -> {
//                        //send to DB
//                        System.out.println(ch);
//                    },
//                    (e) -> {
//                        logger.error(e.toString());
//                    });
//        });
//    }
//    public static List<TryWrap<Character>> filesOnlyTillFirstException(List<String> fileNames) {
//        //List<TryWrap<Character>> firstChars = fileNames.stream()
//        Stream<TryWrap<Character>> firstCharsStream = fileNames.stream()
//                .map(fileName -> {
//                    return TryWrap.of(() -> {
//                        try (FileReader reader = new FileReader(fileName)) {
//                            int ch = reader.read();
//                            return (char) ch;
//                        }
//                    });
//                });
//
////        Stream<TryWrap<Character>> fesStream = makeGoTill(firstCharsStream, tw -> tw.isLeft());
////        List<TryWrap<Character>> firstChars = fesStream.collect(toList());
//
//        List<TryWrap<Character>> firstChars = makeGoTill(firstCharsStream, tw -> tw.isException())
//                .collect(toList());
//
//        return firstChars;
//    }
//}
