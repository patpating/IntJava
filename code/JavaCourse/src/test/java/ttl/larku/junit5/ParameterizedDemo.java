package ttl.larku.junit5;

import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.EnumSource.Mode.EXCLUDE;
import static org.junit.jupiter.params.provider.EnumSource.Mode.MATCH_ALL;

/**
 * To run parameterized tests, you have to add junit-jupiter-params
 * as a dependency
 */

public class ParameterizedDemo {

    @ParameterizedTest
    @ValueSource(strings = {"banana", "orange", "tomato", "radish"})
    void testLength(String text) {
        assertTrue(text.length() > 3);
    }

    /**
     * Customize the display Name of the Test based on the arguments
     * @param text
     */
    @ParameterizedTest(name = "{index} ==> input =''{0}''")
    @ValueSource(strings = {"banana", "orange", "tomato", "radish"})
    void testLengthWithCustomDisplayName(String text) {
        assertTrue(text.length() > 3);
    }


    /**
     * Here we will get a null from the @NullSource,
     * an empty String from @EmptySource,
     * and then a variety of spaces. Can use this
     * to check that your code handles nulls and empties
     * correctly
     * <p>
     * Can get two for one with @NullAndEmptySource
     *
     * @param text
     */
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"banana", "orange", "tomato", "radish"})
    void checkForNullAndEmpty(String text) {
        assertTrue(text == null || text.length() == 0 || text.length() > 3);
    }

    /**
     * Various Examples of using Enum Sources
     */
    @ParameterizedTest
    @EnumSource(TimeUnit.class)
    void testWithEnumSource(TimeUnit timeUnit) {
        System.out.println(timeUnit);
        assertNotNull(timeUnit);
    }

    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, names = {"DAYS", "HOURS"})
    void testWithEnumSourceInclude(TimeUnit timeUnit) {
        assertTrue(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
    }

    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = EXCLUDE, names = {"DAYS", "HOURS"})
    void testWithEnumSourceExclude(TimeUnit timeUnit) {
        assertFalse(EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS).contains(timeUnit));
        assertTrue(timeUnit.name().length() > 5);
    }


    @ParameterizedTest
    @EnumSource(value = TimeUnit.class, mode = MATCH_ALL, names = "^(M|N).+SECONDS$")
    void testWithEnumSourceRegex(TimeUnit timeUnit) {
        String name = timeUnit.name();
        assertTrue(name.startsWith("M") || name.startsWith("N"));
        assertTrue(name.endsWith("SECONDS"));
    }

    /**
     * Method Source - paramters from a method
     * The method HAS to be static unless the test instance is
     * annotated with @TestInstance(Lifecycle.PER_CLASS).
     *
     * @param argument
     */
    @ParameterizedTest
    @MethodSource("inputDataProvider")
    void testWithExplicitLocalMethodSource(InputData argument) {
        System.out.println("argument is " + argument);
        assertNotNull(argument);
    }

    static class InputData {
        public int x;
        public int y;

        public InputData(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "InputData{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    /**
     * The Method Source.  The return type can be an actual
     * Stream, or something that can be converted into a
     * Stream, i.e. any type of Stream, Iterable, Array etc.
     *
     * @return
     */
    //static List<InputData> inputDataProvider() {
    static Stream<InputData> inputDataProvider() {
        return Stream.of(new InputData(0, 2), new InputData(10, 20));
        //return List.of(new InputData(0, 2), new InputData(10, 20));
    }


    /**
     * Multiple Argument injection
     */
    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
        System.out.println(str + ", " + num + ", " + list);
        assertThat(str.length(), Matchers.greaterThan(4));
        assertTrue(num >= 1 && num <= 2);
        assertEquals(2, list.size());
    }

    /**
     * Here we have to return a "2 dimensional" object.
     * We send back a Stream of Arguments, or of Object Array
     *
     * @return
     */
    //static Stream<Arguments> stringIntAndListProvider() {
    static Stream<Object[]> stringIntAndListProvider() {
//        return Stream.of(
//                arguments("apple", 1, Arrays.asList("a", "b")),
//                arguments("banana", 2, Arrays.asList("x", "y")),
//                arguments("orange", 2, Arrays.asList("a", "b")),
//                arguments("tomato", 2, Arrays.asList("x", "y"))
//        );

        return Stream.of(
                new Object[]{"apple", 1, Arrays.asList("a", "b")},
                new Object[]{"banana", 2, Arrays.asList("x", "y")},
                new Object[]{"orange", 2, Arrays.asList("a", "b")},
                new Object[]{"tomato", 2, Arrays.asList("x", "y")}
        );
    }

}
