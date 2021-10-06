package ttl.larku.streams;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

/**
 * @author whynot
 */
public class PrimitiveStreams {

    public void intStream() {
        IntStream iStream = IntStream.range(0, 100);

        IntSummaryStatistics is = iStream.summaryStatistics();
    }
}
