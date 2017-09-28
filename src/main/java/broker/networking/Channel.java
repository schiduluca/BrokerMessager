package broker.networking;

import java.util.Map;

public interface Channel {
    Map<String ,ClientConnection> getClients();
    String getChannelName();
}
