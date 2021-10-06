package ttl.larku.solutions.exceptions;


import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import io.vavr.control.Try;

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

	// TODO - No exception should escape, so we have to catch the Exception
	@Test
	public void testCanThrowTheOldWay() {
		try {
			String result = canThrow();
			result = result + "other stuff";
			System.out.println("Result: " + result);
			assertTrue(result.contains("rolled"));
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("No Result: " + e);
			assertTrue(e.getMessage().equals("Bad Roll"));
		}
	}

	// TODO - Implement the test above using TryWrap
	@Test
	public void testCanThrowWithTryWrap() {
		Try<String> tw = Try.of(() -> canThrow());
		tw.map(s -> s + "other stuff").onSuccess(str ->
		{
			System.out.println("Result: " + str);
			assertTrue(str.contains("rolled"));
		}).onFailure(e ->
		{
			System.out.println("No Result: " + e);
			assertTrue(e.getMessage().equals("Bad Roll"));
		});
	}
}
