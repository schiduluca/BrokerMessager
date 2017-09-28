package broker;

import broker.dto.MessageData;
import broker.networking.BrokerChannel;
import broker.networking.ClientConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ClientsHandler {
    private Map<String, ClientConnection> clients = new ConcurrentHashMap<>();
    private List<BrokerChannel> brokerChannels = new ArrayList<>();

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
                    case CREATE_CHANNEL_REQUEST:
                        createChannel(o.getData().getData());
                        break;
                    case SUBSCRIBE_REQUEST:


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

    private void createChannel(String channelName) {
        if(brokerChannels.stream().noneMatch(channel -> channel.getChannelName().equals(channelName))) {
            brokerChannels.add(new BrokerChannel(channelName));
            System.out.println("Channel with name " + channelName + " created");
        } else {
            System.out.println("Channel " + channelName + " already exists.");
        }
    }
}
