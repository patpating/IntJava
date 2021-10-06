package ttl.larku.streams.split;

import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

/**
 * A very simple Spliterator to illustrate the use of tryAdvance.
 *
 * @author whynot
 */
public class SimpleSpliterator implements Spliterator<Integer> {
    Integer [] source = {3, 4, 77, 8, 030, 303};
    int limit = source.length;
    int index;

    /**
     * We call the consumer and return true as long as we have data.  The consumer
     * will be from the stream operation that is downstream of this
     * Spliterator.  Otherwise we return false which indicates end
     * of stream to the downstream operation.
     *
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
        if(index < limit) {
            action.accept(source[index]);
            index++;
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<Integer> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return limit;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED;
    }

    public static void main(String[] args) {
        SimpleSpliterator si = new SimpleSpliterator();

        Stream<Integer> myStream = StreamSupport.stream(si, false);
        List<Integer> ints = myStream
                .map(i -> i * i)
                .collect(toList());
        ints.forEach(System.out::println);
    }
}
