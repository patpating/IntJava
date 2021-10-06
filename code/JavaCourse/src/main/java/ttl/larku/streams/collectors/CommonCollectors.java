package ttl.larku.streams.collectors;

import ttl.larku.app.PlaylistApp;
import ttl.larku.domain.Track;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.OptionalLong;
import java.util.stream.Collectors;

/**
 * @author whynot
 */
public class CommonCollectors {

    public static void main(String[] args) {
//        summaryStats();
        maxByMinByAveraging();
    }

    public static void maxByMinByAveraging() {
        List<Track> tracks = PlaylistApp.tracks();

        OptionalLong oi = tracks.stream()
                .filter(t -> t.getDuration() != null)
                .mapToLong(t -> {
                    Duration duration = t.getDuration();
                    return duration.getSeconds();
                }).max();

        oi.ifPresent(i -> System.out.println("oi: " + i));

        tracks.stream()
                .filter(t -> t.getDuration() != null)
                .collect(Collectors.maxBy((t1, t2) -> {
                    Duration duration1 = t1.getDuration();
                    Duration duration2 = t1.getDuration();
                    return duration1.compareTo(duration2);
                })).ifPresent(t -> System.out.println("track: " + t));
    }

    public static void summaryStats() {
        List<Track> tracks = PlaylistApp.tracks();
        List<Track> emptyTracks = new ArrayList<>();

        LongSummaryStatistics iss = emptyTracks.stream()
                .filter(t -> t.getDuration() != null)
                .mapToLong(t -> {
                    return t.getDuration().getSeconds();
                }).summaryStatistics();

        System.out.println(iss);
    }
}
