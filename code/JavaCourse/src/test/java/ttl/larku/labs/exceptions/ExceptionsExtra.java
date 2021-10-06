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
public class ExceptionsExtra {

    List<String> siteAddresses = List.of("https://google.com", "https://xyz.com", "https://zyx.com");

    //TODO - You need to have completed Exception Lab 3.
    // Copy your implementation of this function from your solution.
    public List<String> processAddresses() {
        return null;
    }

    //TODO - Part B.  How would you change the function above to
    // give you both the errors and the failures?
    // Uncomment the function below and implement it.
//    public XXXXX processAddressesForSuccessAndErrors() {
//    }


    //TODO - Part C.  Change the implementation so you also
    // retain the address for all successes and failures.
//    public XXXX processAddressesAndRetainInfo() {}


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

    //TODO - Part B. Write a Test for your implementation
    // of processAddressForSuccessAndFailures.
    @Test
    public void testGetSuccessAndFailure() {
    }

}
