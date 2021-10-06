package ttl.larku.slidecode;

/**
 * @author whynot
 */
public class CoContraVarInheritance {

    class Base {
        public Number sq(Number n) {
            return n.doubleValue() * n.doubleValue();
        }
    }

    /**
     *
     * Covariant return type is okay.
     * "Co" variant because the class hierarchy
     * and the return type are varying in the
     * the same direction
     */
    class NumberToIntegerSquarer extends Base {
        @Override
        public Integer sq(Number n) {
            return n.intValue() * n.intValue();
        }
    }

    /**
     * For argument types, you could only change
     * the argument by making it "wider".  Only
     * possibility in this case is Object.
     * Java does NOT allow this, but if it
     * did, this would be ContraVariance.
     * "Contra" because the argument type
     * can only vary in the contrary direction.
     */
//    class IntegerToNumberSquarer extends Base {
//        @Override
//        public Number sq(Object n) {
//            return n.intValue() * n.intValue();
//        }
//    }
}
