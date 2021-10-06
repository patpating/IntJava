package ttl.larku.labs.exceptions;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author whynot
 */
public class Exceptions3 {

    List<String> siteAddresses = List.of("https://google.com", "https://xyz.com", "https://zyx.com");

    //TODO - Part A.  This method should process a list of address and
    // return a List of results.  We have to turn each
    // String address into a URI, and then "connect" to that
    // address. We are faking the connecting part for now.
    // For this use case, we don't care about the errors, we
    // just want to throw them away.
    // Add code to this method to do the needful.  Use Streams
    // and make it work without try/catch.
    public List<String> processAddresses() {
        return null;
    }

    public String connectAndGetResult(String address) throws IOException {
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.connect();

        //If we got here, we made a successful connection;
        //We are not actually going to read anything from the connection
        String result = "Address: " + address + ", at: " + LocalTime.now();

        connection.getInputStream().close();
        return result;

    }

    //TODO - Part A. Make this test run successfully
    @Test
    public void testGetOnlySuccesses() {
       List<String> results = processAddresses();

        System.out.println("Results.size: " + results.size());
        results.forEach(System.out::println);

        assertEquals(1, results.size());
    }

}
