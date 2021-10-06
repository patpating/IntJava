package ttl.larku.solutions.generics;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author whynot
 */
public class GenericsLab3 {

    class ValueHolder<T> {
        private T value;

        public ValueHolder(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }


    @Test
    public void testStringValueHolder() {
        ValueHolder<String> vh = new ValueHolder<>("Boo");

        String value = vh.getValue();
        assertEquals("Boo", value);
    }

    @Test
    public void testLocalDateValueHolder() {
        LocalDate ld = LocalDate.now();
        ValueHolder<LocalDate> vh = new ValueHolder<>(ld);

        LocalDate ld2 = vh.getValue();

        assertEquals(2021, ld2.getYear());
    }
}
