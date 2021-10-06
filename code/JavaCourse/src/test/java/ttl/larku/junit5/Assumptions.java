package ttl.larku.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * JUnit5 Assumptions can be used to assert truths about the environment
 * before running a test
 *
 * Taken from https://junit.org/junit5/docs/current/user-guide
 */
public class Assumptions {

    @BeforeEach
    public void init() {
        System.setProperty("ENV", "CI");
    }

    @Test
    void testOnlyOnCiServer() {
        assumeTrue("CI".equals(System.getProperty("ENV")));
        // remainder of test
    }

    @Test
    void testOnlyOnDeveloperWorkstation() {
        assumeTrue("DEV".equals(System.getenv("ENV")),
                () -> "Aborting test: not on developer workstation");
        // remainder of test
    }

    @Test
    void testInAllEnvironments() {
        Executable e;
        assumingThat("CI".equals(System.getenv("ENV")),
                () -> {
                    // perform these assertions only on the CI server
                    assertEquals(2, 4 / 2);
                });

        // perform these assertions in all environments
        assertEquals(42, 6 * 7);
    }
}
