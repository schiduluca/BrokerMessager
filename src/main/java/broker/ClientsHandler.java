package broker;

import broker.dto.MessageData;
import broker.networking.ClientConnection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ClientsHandler {
    private Map<String, ClientConnection> clients = new ConcurrentHashMap<>();

    public void addClient(ClientConnection clientConnection) {

        checkForIncomingMessages(clientConnection);
    }


    private void checkForIncomingMessages(ClientConnection clientConnection) {
        while (true) {
            try {
                MessageData o = (MessageData) clientConnection.getObjectInputStream().readObject();
                switch (o.getMessageType()) {
                    case INITIALIZATION:
                        clients.put(o.getData().getData(), clientConnection);
                        clientConnection.setConnectionName(o.getData().getData());
                        System.out.println("Client connected: " + clientConnection.getConnectionName());
                        System.out.println("Number of clients connected: " + clients.size());

                        break;
                    case SIMPLE_MESSAGE:
                        System.out.println(o.getData().getData());
                        clients.entrySet().stream().filter(name -> o.getReceivers().contains(name.getKey()))
                                .forEach(connection -> connection.getValue().sendToClient(o));
                        break;
                    default:
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                clientConnection.closeConnection();
                System.out.println("Client disconnected: " + clientConnection.getConnectionName());
                clients.values().remove(clientConnection);
                System.out.println("Number of clients connected: " + clients.size());

                return;
            }
        }
    }
}
