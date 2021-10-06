package ttl.larku.junit5;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Examples of injecting various helper objects
 * - TestInfoDemo gives access to some
 * - TestReporter for writing to Standard output
 *
 * context information for the test
 */
@DisplayName("JUnit5TestInfo Demo")
public class TestInfoDemo {

    //Available in Constructor
    TestInfoDemo(org.junit.jupiter.api.TestInfo testInfo) {
        assertEquals("JUnit5TestInfo Demo", testInfo.getDisplayName());
        /*Jdk 9+
        testInfo.getTestClass().ifPresentOrElse(System.out::println, () -> {
            fail("No class found for test");
        });
         */

        //Here, we do a sneaky thing of returning a Runnable from the map.
        //What we return is a lambda from both the map and the orElse, and
        //at the end we call run() on whichever is returned.  Cool
        testInfo.getTestClass().<Runnable>map(cl -> () -> System.out.println(cl))
                .orElse(() -> fail("no class found for test")).run();

        assertEquals(this.getClass(), testInfo.getTestClass().get());
        assertTrue(testInfo.getDisplayName().contains("Demo"));

        org.hamcrest.MatcherAssert.assertThat(0, org.hamcrest.CoreMatchers.equalTo(0));
    }

    @BeforeEach
    void init(org.junit.jupiter.api.TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        //assertTrue(displayName.equals("TEST 1") || displayName.equals("test2()"));
    }

    @Test
    @DisplayName("TEST 1")
    @Tag("my-tag")
    void test1(org.junit.jupiter.api.TestInfo testInfo) {
        assertEquals("TEST 1", testInfo.getDisplayName());
        assertTrue(testInfo.getTags().contains("my-tag"));
    }

    @Test
    void test2() {
    }

    @Test
    public void testReporter(TestReporter reporter, TestInfo testInfo) {
        reporter.publishEntry("Reporter is reporting from " + testInfo.getTestMethod().get().getName());
    }
}
