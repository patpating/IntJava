package ttl.larku.streams.split;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A Custom Spliterator that will use a supplied Predicate to
 * decide when to tell the downstream operation that the
 * stream is done.  Can be use to implement a "break" like
 * feature.  JDK9+ has .takeWhile(Predicate).
 * <p>
 * But here we do a slightly different thing and also return
 * the last thing (the out of band value) that made the stream shut down, unlike
 * 'takeWhile' which would throw the out of band value away.
 * <p>
 * The reasoning here is that you could use to this to process a stream
 * until an Exception occurred, and then return previous result and
 * then the Exception.
 *
 * @author whynot
 */
public class GoTill<T> implements Spliterator<T> {


    private final Stream<T> sourceStream;
    private final Spliterator<T> sourceIter;

    private Predicate<T> decider;

    boolean shutdown = false;
    boolean shuttingDown = false;

    /**
     * Wrap a new Stream around the given argument.  Use the 'decider' Predicate
     * to decide if this Stream should proceed or not.
     *
     * @param sourceStream  This is where the data comes from
     * @param decider       A predicate to decide when to quit
     * @return              A Stream of the original type with a GoTill Spliterator
     *                      wrapped around the original Spliterator
     */
    public static <U> Stream<U> makeGoTill(Stream<U> sourceStream, Predicate<U> decider) {
        GoTill<U> fes = new GoTill<>(sourceStream, decider);
        Stream<U> stream = StreamSupport.stream(fes, false);
        return stream;
    }

    public GoTill(Stream<T> sourceStream, Predicate<T> decider) {
        this.sourceStream = sourceStream;
        this.sourceIter = sourceStream.spliterator();
        this.decider = decider;
    }

    /**
     * Here we call the tryAdvance of the source Spliterator.
     * The sourceIter.tryAdvance will only call our
     * consumer if it finds a next element.  We then check
     * that element against our 'decider'.  If the decider
     * returns true, we keep going.  If not, then we have
     * just seen our sentinel signal, so we set the
     * appropriate flags to return a false on the next
     * call to tryAdvance.
     * <p>
     * That implies that we will let the sentinel value
     * through, and stop after that.
     *
     * @param action
     * @return
     */
    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (shuttingDown) {
            return false;
        }

        boolean sourceResult = sourceIter.tryAdvance(data -> {
            action.accept(data);
            if (decider.test(data)) {
                shuttingDown = true;
            }
        });

        return sourceResult;
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return sourceIter.estimateSize();
    }

    @Override
    public int characteristics() {
        return sourceIter.characteristics();
    }
}
