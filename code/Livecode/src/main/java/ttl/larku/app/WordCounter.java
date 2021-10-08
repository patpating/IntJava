package ttl.larku.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author whynot
 */
public class WordCounter {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("words.txt");
        var x = Files.lines(path).skip(1)
                .flatMap(line -> Stream.of(line.split("\\W")))
                .filter(word -> !word.matches("^\\s*$") && !word.matches("^[0-9]*"))
                .collect(Collectors.groupingBy(w -> w, TreeMap::new, Collectors.counting()));

        x.forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
