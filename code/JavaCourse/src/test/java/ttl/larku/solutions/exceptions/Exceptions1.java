package ttl.larku.solutions.exceptions;



import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Exception Lab - TODO comments below have instructions.
 *
 * @author whynot
 */
public class Exceptions1 {

    class ApplicationException extends Exception {
        public ApplicationException(String message) {
            super(message);
        }

        public ApplicationException(Exception cause) {
            super(cause);
        }
    }

    /**
     * TODO - Uncomment the code in the test and make it compile and
     *  run such that the test completes successfully.  Do not change
     *  any of the existing code, or the signature of the test method
     *
     * @throws ApplicationException
     */
    @Test
    public void testTryWithResources() {
        assertThrows(ApplicationException.class, () -> {
        try {
            URL url = getClass().getClassLoader().getResource("wors");
            Path path = Paths.get(url.toURI());
            path = Paths.get("/xyz");
            BufferedReader reader = Files.newBufferedReader(path);
            int count = 0;
            while (reader.readLine() != null) {
                count++;
            }
            assertEquals(99172, count);
        }catch(RuntimeException | URISyntaxException | IOException e) {
            throw new ApplicationException(e);
        }
        });
    }
}
