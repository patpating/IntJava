package ttl.larku.misc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author whynot
 */
public class StreamStep {

    @Test
    public void foo() {
        List<String> ls = Arrays.asList("one", "tow", "three");

        ls.stream()
                .map(s -> s.toLowerCase())
                .collect(Collectors.toList());
    }
}
