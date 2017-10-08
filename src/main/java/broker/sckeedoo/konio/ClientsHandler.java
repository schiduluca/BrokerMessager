package broker.sckeedoo.konio;

import broker.sckeedoo.konio.commons.db.DatabaseHelper;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.networking.BrokerChannel;
import broker.sckeedoo.konio.networking.connection.ClientConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ClientsHandler {
    private List<ClientConnection> allClients = new ArrayList<>();
    private List<BrokerChannel> brokerChannels = new ArrayList<>();
    private List<MessageData> messageData = new ArrayList<>();

    public void addClient(ClientConnection clientConnection) {
        checkForIncomingMessages(clientConnection);
    }


    private void checkForIncomingMessages(ClientConnection clientConnection) {
        while (true) {
            try {
                MessageData o = (MessageData) clientConnection.getObjectInputStream().readObject();
                switch (o.getMessageType()) {
                    case INITIALIZATION:
                        clientConnection.setConnectionName(o.getData());
                        allClients.add(clientConnection);
                        System.out.println("Client connected: " + clientConnection.getConnectionName());
                        System.out.println("Number of allClients connected: " + allClients.size());
                        break;
                    case SIMPLE_MESSAGE:
                        DatabaseHelper.getINSTANCE().save(o);
                        messageData.add(o);
                        dispatchMessage(o);
                        break;
                    case CREATE_CHANNEL_REQUEST:
                        createChannel(o.getData());
                        break;
                    case CLOSE_CONNECTION:
                        System.out.println("Client disconnected");
                        handleDisconnection(clientConnection);
                        return;
                    default:
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Abnormal disconnection");
                handleDisconnection(clientConnection);
                return;
            }
        }
    }


    private void handleDisconnection(ClientConnection clientConnection) {
        clientConnection.closeConnection();
        System.out.println("Client disconnected: " + clientConnection.getConnectionName());
        allClients.remove(clientConnection);
        System.out.println("Number of allClients connected: " + allClients.size());
    }

    private void dispatchMessage(MessageData o) {
        allClients.stream()
                .filter(name -> o.getReceivers().contains(name.getConnectionName()))
                .forEach(clientConnection -> clientConnection.sendToClient(o));
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
