package ttl.larku.slidecode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author whynot
 */
public class StreamsSnippets {

    public void foo() {
        List<Integer> ints = List.of(20, 49, 484, 2085, 48, 12);

        List<Integer> squaresForGT25 = ints.stream()
                .filter(i -> i > 25)
                .map(i -> i * i)
                .collect(Collectors.toList());
    }
}
