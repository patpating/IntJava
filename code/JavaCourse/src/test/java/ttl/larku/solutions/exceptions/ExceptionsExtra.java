package ttl.larku.solutions.exceptions;


import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

import static java.util.stream.Collectors.toList;

/**
 * @author whynot
 */
public class ExceptionsExtra {

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
        List<String> results = siteAddresses.stream()
                .map(address -> Try.of(() -> connectAndGetResult(address)))
                .filter(t -> t.isSuccess())
                .map(t -> t.get())
                .collect(toList());
        return results;
    }

    //TODO - Part B.  How would you change the function above to
    // give you both the errors and the failures?
    //Note - One way to do this is to return a List of Try objects.
    // Variations on that might be to process the Try's here and return
    // the back something that is simpler to deal with. -- Extra Credit
    public List<Try<String>> processAddressesForSuccessAndErrors() {
        List<Try<String>> results = siteAddresses.stream()
                .map(address -> Try.of(() -> connectAndGetResult(address)))
                .collect(toList());
        return results;
    }


    //TODO - Part C.  Change the implementation so you also
    // retain the address for all successes and failures.
    //Note - We want to return a Tuple of <Address, Result>
    // or <Address, Exception>.  Several ways to do that.
    // We will do it by creating a Pair class.
    public List<Pair<String, Try<String>>> processAddressesAndRetainInfo() {
        List<Pair<String, Try<String>>> results = siteAddresses.stream()
                .map(address -> new Pair<String, Try<String>>(address, Try.of(() -> connectAndGetResult(address))))
                .collect(toList());
        return results;

    }


    static class Pair<A, B> {
        public final A first;
        public final B second;

        public Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }
    }


    public String connectAndGetResult(String address) throws IOException {
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.connect();
        InputStream iStream = connection.getInputStream();

        Scanner scanner = new Scanner(iStream);
        String firstLine = scanner.nextLine();

        iStream.close();

        return firstLine;
    }

    //TODO - Part B. Write a Test for your implementation
    // of processAddressForSuccessAndFailures.
    @Test
    public void testGetSuccessAndFailure() {
        List<Try<String>> results = processAddressesForSuccessAndErrors();
        results.forEach(tr -> {
            tr.onSuccess(str -> {
                System.out.println("Success: " + str);
            }).onFailure(ex -> {
                System.out.println("Got error: " + ex);
            });
        });
    }

    //TODO - Part C.  Write a test for returning Pairs
    @Test
    public void testGetSuccessAndFailureWithAddressInfo() {
        List<Pair<String, Try<String>>> results = processAddressesAndRetainInfo();
        results.forEach(p -> {
            p.second.onSuccess(str -> {
                System.out.println("Address: " + p.first + ", Success: " + str);
            }).onFailure(ex -> {
                System.out.println("Address: " + p.first + ", Error: " + ex);
            });
        });
    }

}
