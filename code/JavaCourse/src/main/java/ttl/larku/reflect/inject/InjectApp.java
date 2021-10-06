package ttl.larku.reflect.inject;

/**
 * @author whynot
 */
public class InjectApp {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        SomeController sc = BeanFactory.getBean(SomeController.class);

        sc.doStuff();
    }
}
