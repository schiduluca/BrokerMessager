package broker.sckeedoo.konio;


import broker.sckeedoo.konio.networking.connection.ClientConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerMQ {
    private ServerSocket serverSocket;
    private ClientsHandler clientsHandler;


    public BrokerMQ() {
        clientsHandler = new ClientsHandler();

        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startBroker() {
        while (true) {
            try {
                Socket accept = serverSocket.accept();

                new Thread(() -> {
                    ClientConnection connection = new ClientConnection(accept);
                    clientsHandler.addClient(connection);
                }).start();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
