package ttl.larku.junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import ttl.larku.junit5.extensions.TestTimerExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
/**
 * Order of calls.  Taken from https://junit.org/junit5/docs/current/user-guide/
 */

@DisplayName("JUnit5Basics Demo")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)  //Created fresh for each test. The Default.
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)  //One instance created for all tests
public class JUnit5Basics {

    //Called before anything else.  _
    //HAS to be static for TestInstance.LifeCycle.PER_METHOD
    @BeforeAll
    static void initAll() {
        System.out.println("In Before All");
    }

    //Called Before Each test.
    @BeforeEach
    void init() {
    }

    //All is well.
    @Test
    @DisplayName("Address is good")
    @ExtendWith(TestTimerExtension.class)
    void testAddressNotNull() {
    }

    //We can make a test fail.
    @Test
    void failingTest() {
        fail("a failing test");
    }

    //Test that throws an exception
    @Test
    public void expectException() {
        assertThrows(RuntimeException.class, () -> {
            Integer.valueOf("abc");
        });
    }

    //Ignored.  Using the message attribute is a "good thing"
    @Test
    @Disabled("Ignoring this one")
    void skippedTest() {
        // not executed
    }

    //Aborted due to a failed Assumption.
    //Assumptions can be used to assert truths about the environment
    //before a test is run.
    @Test
    void abortedTest() {
        String profile = System.getProperty("PROFILE");
        assumeTrue(profile != null && profile.equals("DEV"),
                "profile is not DEV");
        fail("test should have been aborted");
    }

    //Called After Each test.
    @AfterEach
    void tearDown() {
    }

    //Called after all tests are done.
    //HAS to be static for TestInstance.LifeCycle.PER_METHOD
    @AfterAll
    static void tearDownAll() {
    }

    @Test
    public void integTest(TestInfo info) {
        System.out.println(info.getDisplayName() + " with Tag " + info.getTags() + " called");
    }
}
