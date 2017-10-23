package broker.sckeedoo.konio;


import broker.sckeedoo.konio.networking.connection.ClientConnection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BrokerMQ {
    private ServerSocket serverSocket;
    private ClientsHandler clientsHandler;
    private static final Logger logger = Logger.getLogger(BrokerMQ.class.getClass().getName());



    public BrokerMQ(int port) {
        logger.info("Broker started");
        clientsHandler = new ClientsHandler();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startBroker() {
        while (!serverSocket.isClosed()) {
            try {
                Socket accept = serverSocket.accept();
                ClientConnection connection = new ClientConnection(accept);
                clientsHandler.addClient(connection);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
