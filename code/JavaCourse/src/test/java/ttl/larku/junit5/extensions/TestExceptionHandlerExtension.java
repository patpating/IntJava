package ttl.larku.junit5.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.io.FileNotFoundException;

/**
 * These Callbacks exist and are called in this order:
 *
 * BeforeAllCallback
 *   BeforeEachCallback
 *     BeforeTestExecutionCallback
 *        Execute the Test
 *      TestExecutionExceptionHandler   <-- We are implementing this
 *     AfterTestExecutionCallback
 *   AfterEachCallback
 * AfterAllCallback
 *
 * This guy's get's called right after the test has completed,
 * and before the afterTestExecution
 */
public class TestExceptionHandlerExtension implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable)
            throws Throwable {

        //we are okay if it is a FileNotFoundException
        if (throwable instanceof FileNotFoundException) {
            return;
        }

        //Else rethrow
        throw throwable;
    }
}
