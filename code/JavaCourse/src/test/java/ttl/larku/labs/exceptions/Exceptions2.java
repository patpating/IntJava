package ttl.larku.labs.exceptions;


import java.util.concurrent.ThreadLocalRandom;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
public class Exceptions2 {

    public String canThrow() throws Exception {
        int roll = ThreadLocalRandom.current().nextInt(0, 10);
        if (roll < 5) {
            return "rolled: " + 5;
        } else {
            throw new Exception("Bad Roll");
        }
    }


    //TODO - No exception should escape, so we have to catch the Exception
    @Test
    public void testCanThrowTheOldWay() {
        String result = null;
        try {
            result = canThrow();
            result = result + "other stuff";
            System.out.println("Result: " + result);
            assertTrue(result.contains("rolled"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No Result: " + result);
            assertEquals("Bad Roll", e.getMessage());
        }
    }


    //TODO - Implement the test above using Vavr Try
    public void testCanThrowWithVavrTry() {
    }
}
