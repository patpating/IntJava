package ttl.larku.labs.generics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * TODO - Generics Lab 3.  Instructions are in TODO comments below.
 *
 * @author whynot
 */
public class GenericsLab3 {

    //TODO - Change this class so that the 'testLocalDateValueHolder'
    // method below compiles and runs successfully.
    class ValueHolder {
        private String value;

        public ValueHolder(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    @Test
    public void testStringValueHolder() {
        ValueHolder vh = new ValueHolder("Boo");

        String value = vh.getValue();
        assertEquals("Boo", value);
    }

    //TODO - Uncomment the test below and make changes
    // to the code so it compiles and runs successfully.
    // You will first have to make changes to the ValueHolder
    // class above.
    @Test
    public void testLocalDateValueHolder() {
//        LocalDate ld = LocalDate.now();
//        ValueHolder vh = new ValueHolder(ld);
//
//        LocalDate ld2 = vh.getValue();
//
//        assertEquals("2020", ld2.getYear());
    }
}
