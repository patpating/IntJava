package ttl.larku.junit5;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Specifying Test order
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestMethodOrder(MethodOrderer.Alphanumeric.class)
//@TestMethodOrder(MethodOrderer.Random.class)
public class TestOrder {

    @Test
    //These work when using MethodOrderer.OrderAnnotation.class
    @Order(1)
    void nullValues() {
        // perform assertions against null values
        System.out.println("NullValues");
    }

    @Test
    @Order(2)
    void emptyValues() {
        // perform assertions against empty values
        System.out.println("EmptyValues");
    }

    @Test
    @Order(3)
    void validValues() {
        // perform assertions against valid values
        System.out.println("ValidValues");
    }

}
