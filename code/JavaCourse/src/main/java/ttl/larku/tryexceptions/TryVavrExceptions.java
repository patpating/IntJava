package ttl.larku.tryexceptions;

import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class TryVavrExceptions {

    private static Logger logger = LoggerFactory.getLogger(TryVavrExceptions.class);

    public static void main(String[] args) {
        List<String> fileNames = Arrays.asList(".gitignore", "doesNotExist", "pom.xml");
        callFilesWithExceptionsVavr(fileNames);
    }

    /**
     * Here we use a Monad (or container) to hold either the result or the Exception.
     * In this case we are going to use Try from the Vavr library
     *
     * @param fileNames
     * @return  A list of TryWrap<String> objects.
     */
    public static List<Try<String>> filesWithExceptionsVavr(List<String> fileNames) {
        List<Try<String>> firstChars = fileNames.stream()
                .map(fileName -> {
                    return Try.of(() -> {
                        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                            String line = reader.readLine();
                            return line;
                        }
                    });
                })
                .collect(toList());

        return firstChars;
    }

    public static void callFilesWithExceptionsVavr(List<String> fileNames) {
        List<Try<String>> firstChars = filesWithExceptionsVavr(fileNames);

        firstChars.stream()
                .map(tw -> tw.map(String::toUpperCase))
                .forEach(tw -> {
                    tw.onSuccess(firstLine -> {//send to DB
                        System.out.println(firstLine);
                    })
                            .orElseRun(e -> {
                                logger.error(e.toString());
                            });
                });
    }
}
