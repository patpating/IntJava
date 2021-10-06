//package ttl.larku.streams.split;
//
//import ttl.trywrap.TryWrap;
//
//import java.util.Spliterator;
//import java.util.function.Consumer;
//
///**
// * A Custom Spliterator that only deals with TryWraps.  It will go to the 
// * end or the first TryWrap that has an Exception.  It will emit that last
// * TryWrap and then exit on the next call to tryAdvance.  This will give
// * the user everything that worked till that point and then the Exception
// * that occurred.
// *
// * @author whynot
// */
//public class TryWrapToFirstException implements Spliterator<TryWrap<?>> {
//
//
//    private final Spliterator<TryWrap<?>> source;
//    boolean shutdown = false;
//    boolean shuttingDown = false;
//
//    public TryWrapToFirstException(Spliterator<TryWrap<?>> source) {
//        this.source = source;
//    }
//
//    @Override
//    public boolean tryAdvance(Consumer<? super TryWrap<?>> action) {
//        if(shuttingDown) {
//            return false;
//        }
//        boolean sourceResult = source.tryAdvance(tw -> {
//                action.accept(tw);
//            if(tw.isException()) {
//                shuttingDown = true;
//            }
//        });
//
//        return sourceResult;
//    }
//
//    @Override
//    public Spliterator<TryWrap<?>> trySplit() {
//        return null;
//    }
//
//    @Override
//    public long estimateSize() {
//        return 0;
//    }
//
//    @Override
//    public int characteristics() {
//        return 0;
//    }
//}
