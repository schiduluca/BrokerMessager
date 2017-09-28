package broker.networking;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BrokerChannel implements Channel {

    private String channelName;
    private Map<String, ClientConnection> clients = new ConcurrentHashMap<>();

    public BrokerChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public Map<String, ClientConnection> getClients() {
        return clients;
    }

    @Override
    public String getChannelName() {
        return this.channelName;
    }
}

