package ttl.larku.generics;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import ttl.larku.app.PlaylistApp;
import ttl.larku.domain.Track;

/**
 * @author whynot
 */
public class MakeAComparator {

    public static void main(String[] args) {
        new MakeAComparator().go();
    }

    public void go() {
        List<Track> tracks = PlaylistApp.tracks();
        //tracks.sort(makeComparator(Track::getDuration));
        tracks.sort(makeComparatorHandlingNulls(Track::getDuration));
        tracks.forEach(System.out::println);

    }

    public <T, R extends Comparable<R>> Comparator<T> makeComparator(Function<T, R> extractor) {
        Comparator<T> comp = (t1, t2) -> extractor.apply(t1).compareTo(extractor.apply(t2));
        return comp;
    }

    public <T, R extends Comparable<R>> Comparator<T> makeComparatorHandlingNulls(Function<T, R> extractor) {
        Comparator<T> comp = (t1, t2) -> {
            R r1 = extractor.apply(t1);
            R r2 = extractor.apply(t2);
            if(r1 != null && r2 != null) {
                return r1.compareTo(r2);
            }
            else if(r1 == null) {
                return -1;
            } else {
                return 1;
            }
        };
        return comp;
    }
}
