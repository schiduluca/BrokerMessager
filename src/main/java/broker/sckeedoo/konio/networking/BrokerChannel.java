package broker.sckeedoo.konio.networking;

import broker.sckeedoo.konio.networking.connection.ClientConnection;

import java.util.ArrayList;
import java.util.List;

public class BrokerChannel implements Channel {

    private String channelName;
    private List<ClientConnection> clients = new ArrayList<>();

    public BrokerChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public List<ClientConnection> getClients() {
        return clients;
    }

    @Override
    public String getChannelName() {
        return this.channelName;
    }

    @Override
    public void subScribeClient(ClientConnection clientConnection) {
        clients.add(clientConnection);
    }

    @Override
    public void unsubscribeClient(ClientConnection clientConnection) {
        clients.remove(clientConnection);
    }
}

