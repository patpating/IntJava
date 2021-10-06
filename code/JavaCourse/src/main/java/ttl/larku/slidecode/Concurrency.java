package ttl.larku.slidecode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author whynot
 */
public class Concurrency {

    private AtomicInteger threadId = new AtomicInteger(1);
    private ExecutorService service;

    public ExecutorService createService()  {
        service = Executors.newCachedThreadPool(runnable -> {
            Thread newThread = new Thread(runnable, "MyThreadPool-" + threadId.getAndIncrement());
            return newThread;
        });
        return service;
    }
}

