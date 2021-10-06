package ttl.larku.streams.split;

import java.util.Spliterator;
import java.util.function.Consumer;

import io.vavr.control.Try;

/**
 * A Custom Spliterator that only deals with TryWraps.  It will go to the 
 * end or the first TryWrap that has an Exception.  It will emit that last
 * TryWrap and then exit on the next call to tryAdvance.  This will give
 * the user everything that worked till that point and then the Exception
 * that occurred.
 *
 * @author whynot
 */
public class VavrTryToFirstException implements Spliterator<Try<?>> {


    private final Spliterator<Try<?>> source;
    boolean shutdown = false;
    boolean shuttingDown = false;

    public VavrTryToFirstException(Spliterator<Try<?>> source) {
        this.source = source;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Try<?>> action) {
        if(shuttingDown) {
            return false;
        }
        boolean sourceResult = source.tryAdvance(tw -> {
                action.accept(tw);
            if(tw.isFailure()) {
                shuttingDown = true;
            }
        });

        return sourceResult;
    }

    @Override
    public Spliterator<Try<?>> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return 0;
    }
}
