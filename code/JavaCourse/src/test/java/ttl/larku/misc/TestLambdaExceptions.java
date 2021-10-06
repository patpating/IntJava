package ttl.larku.misc;

import org.junit.jupiter.api.Test;
import ttl.larku.tryexceptions.ExWrapper;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static ttl.larku.tryexceptions.ExWrapper.wrapCons;
import static ttl.larku.tryexceptions.ExWrapper.wrapFun;

/**
 * @author whynot
 */
public class TestLambdaExceptions {

    @Test
    public void testConsumer() {
        assertThrows(MyTestException.class, () -> {
            Stream.of((String)null).forEach(wrapCons(s -> checkValue(s)));
            //Stream.of((String)null).forEach(wrap((String s) -> checkValue(s)));
        });
    }

    private void checkValue(String value) throws MyTestException {
        if(value==null) {
            throw new MyTestException();
        }
    }

    private class MyTestException extends Exception { }

    @Test
    public void testConsumerRaisingExceptionInTheMiddle() {
        MyLongAccumulator accumulator = new MyLongAccumulator();
        try {
            Stream.of(2L, 3L, 4L, null, 5L).forEach(wrapCons(s -> accumulator.add(s)));
            fail();
        } catch (MyTestException e) {
            assertEquals(9L, accumulator.acc);
        }
    }

    private class MyLongAccumulator {
        private long acc = 0;
        public void add(Long value) throws MyTestException {
            if(value==null) {
                throw new MyTestException();
            }
            acc += value;
        }
    }

    @Test
    public void testFunction() throws MyTestException {
        ExWrapper.Function_WithExceptions<String, Integer, MyTestException> fwe =
                (s) -> transform(s);

        List<Integer> sizes = Stream.of("ciao", "hello")
                .<Integer>map(wrapFun(s -> transform(s))).collect(toList());
                //.<Integer>map(wrap(fwe)).collect(toList());
        assertEquals(2, sizes.size());
        assertEquals(4, sizes.get(0).intValue());
        assertEquals(5, sizes.get(1).intValue());
    }

    private Integer transform(String value) throws MyTestException {
        if(value==null) {
            throw new MyTestException();
        }
        return value.length();
    }

    @Test
    public void testFunctionRaisingException() {
        assertThrows(MyTestException.class, () -> {
            Stream.of("ciao", null, "hello")
                    .map(wrapFun(s -> transform(s))).collect(toList());
        });
    }
}
