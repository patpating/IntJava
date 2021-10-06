package ttl.larku.junit5;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * You can tag classes and/or methods with a name.  This can
 * be used by Launchers to filter tests based on Tag name.
 * e.g. Eclipse or IntelliJ launchers allow you to choose which
 * Tags to run.  And see CustomDiscoveryLaunch.java for a 
 * homebuilt launcher which can use Tags to Filter
 * @author whynot
 *
 */
public class JUnit5Tags {

	@Test
	@Tag("integration")
	public void integTest1() {
		try {
			Thread.sleep(100);
		}
		catch(InterruptedException e) {}
		assertTrue(4 == 2 * 2);
	}

	@Test
	@Tag("integration")
	public void integTest2() {
		try {
			Thread.sleep(200);
		}
		catch(InterruptedException e) {}
		assertTrue(4 == 2 * 2);
	}

	@Test
	@Tag("fast")
	public void fastTest1() {
		try {
			Thread.sleep(10);
		}
		catch(InterruptedException e) {}
		assertTrue(4 == 2 * 2);
		assertTrue(2 != 2);
	}
}
