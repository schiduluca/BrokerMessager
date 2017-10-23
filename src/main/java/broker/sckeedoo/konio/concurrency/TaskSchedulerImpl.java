package broker.sckeedoo.konio.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskSchedulerImpl implements TaskScheduler {

    private Map<String, Future<?>> stringFutureMap = new HashMap<>();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();


    @Override
    public void submitTask(String connectionName, Runnable clientConnection) {
        Future<?> submit = threadPool.submit(clientConnection);
        stringFutureMap.put(connectionName, submit);
    }

    @Override
    public void unsubmitTask(String connectionName) {
        Future<?> future = stringFutureMap.get(connectionName);
        future.cancel(true);
    }
}
