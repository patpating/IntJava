package ttl.larku.junit5.extensions;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.lang.reflect.Method;
import java.util.logging.Logger;


/**
 * These Callbacks exist and are called in this order:
 *
 * BeforeAllCallback
 *   BeforeEachCallback
 *     BeforeTestExecutionCallback    <-- We are implementing this
 *        Execute the Test
 *      TestExecutionExceptionHandler
 *     AfterTestExecutionCallback     <-- And this
 *   AfterEachCallback
 * AfterAllCallback
 *
 * This one test the time between the before/afterTextExecution callbacks
 */

public class TestTimerExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger LOG = Logger.getLogger(TestTimerExtension.class.getName());

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        getStore(context).put(context.getTestMethod().get(), System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getTestMethod().get();
        long start = getStore(context).remove(testMethod, long.class);
        long duration = System.currentTimeMillis() - start;

        LOG.info(() -> String.format("Method [%s] took %s ms.", testMethod.getName(), duration));

    }

    private Store getStore(ExtensionContext context) {
        return context.getStore(Namespace.create(getClass(), context));
    }

}
