package broker.sckeedoo.konio;

import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.networking.BrokerChannel;
import broker.sckeedoo.konio.networking.connection.ClientConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ClientsHandler {
    private List<ClientConnection> allClients = new ArrayList<>();
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
                        clientConnection.setConnectionName(o.getData().getMessage());
                        allClients.add(clientConnection);
                        System.out.println("Client connected: " + clientConnection.getConnectionName());
                        System.out.println("Number of allClients connected: " + allClients.size());
                        break;
                    case SIMPLE_MESSAGE:
                        dispatchMessage(o);
                        break;
                    case CREATE_CHANNEL_REQUEST:
                        createChannel(o.getData().getMessage());
                        break;
                    case SUBSCRIBE_REQUEST:
                        subscribeToChannel(clientConnection, o);
                        break;

                    default:
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                clientConnection.closeConnection();
                System.out.println("Client disconnected: " + clientConnection.getConnectionName());
                allClients.remove(clientConnection);
                System.out.println("Number of allClients connected: " + allClients.size());

                return;
            }
        }
    }

    private void dispatchMessage(MessageData o) {
        List<ClientConnection> filteredClients = allClients.stream()
                .filter(name -> o.getReceivers().contains(name.getConnectionName()))
                .collect(Collectors.toList());

        List<ClientConnection> collect = brokerChannels.stream()
                .filter(channel -> o.getChannels().contains(channel.getChannelName()))
                .flatMap(channel -> channel.getClients().stream())
                .distinct().collect(Collectors.toList());

        filteredClients.stream().filter(collect::contains)
                .forEach(clientConnection -> clientConnection.sendToClient(o));


    }

    private void subscribeToChannel(ClientConnection clientConnection, MessageData o) {
        List<String> collect = Arrays.stream(o.getData().getMessage().split(","))
                .collect(Collectors.toList());
        brokerChannels.stream().filter(element -> collect.contains(element.getChannelName()))
                .forEach(brokerChannel -> {
                    brokerChannel.subScribeClient(clientConnection);
                    System.out.println("Client " + clientConnection.getConnectionName() + " added to channel " + brokerChannel.getChannelName());
                });
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
