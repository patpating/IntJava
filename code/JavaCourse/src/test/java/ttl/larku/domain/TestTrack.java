package ttl.larku.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author whynot
 */
public class TestTrack {

    @Test
    public void testDuration() {
        Track t = Track.album("Stella by Starlight")
                .duration(Duration.ofMinutes(10).plusSeconds(22))
                .artists(Arrays.asList("Frank Sinatra")).build();

        System.out.println(t);
        assertEquals(622, t.getDuration().getSeconds());
    }

    @Test
    public void testDurationWithString() {
        Track t = Track.album("Stella by Starlight")
                .duration("0:10:22")
                .artists(Arrays.asList("Frank Sinatra")).build();

        System.out.println(t);
        assertEquals(622, t.getDuration().getSeconds());
    }

    @Test
    public void testWithLocalDate() {
        Track t = Track.album("Stella by Starlight")
                .duration("0:10:22")
                .date(LocalDate.of(1922, 10, 20))
                .artists(Arrays.asList("Frank Sinatra")).build();

        System.out.println(t);
        assertEquals(622, t.getDuration().getSeconds());
        assertEquals(Month.OCTOBER, t.getDate().getMonth());
    }

    @Test
    public void testWithLocalDateParse() {
        Track t = Track.album("Stella by Starlight")
                .duration("0:10:22")
                .date(LocalDate.parse("1922-10-20"))
                .artists(Arrays.asList("Frank Sinatra")).build();

        System.out.println(t);
        assertEquals(622, t.getDuration().getSeconds());
        assertEquals(Month.OCTOBER, t.getDate().getMonth());
    }

    @Test
    public void testWithLocalDateStr() {
        Track t = Track.album("Stella by Starlight")
                .duration("0:10:22")
                .date("1922-10-20")
                .artists(Arrays.asList("Frank Sinatra")).build();

        System.out.println(t);
        assertEquals(622, t.getDuration().getSeconds());
        assertEquals(Month.OCTOBER, t.getDate().getMonth());
    }

}
