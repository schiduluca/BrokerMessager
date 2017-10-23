package broker.sckeedoo.konio.concurrency;

import broker.sckeedoo.konio.networking.connection.ClientConnection;

public interface TaskScheduler {
    void submitTask(String connectionName, Runnable clientConnection);
    void unsubmitTask(String stringName);
}
