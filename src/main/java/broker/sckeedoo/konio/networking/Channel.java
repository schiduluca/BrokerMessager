package broker.sckeedoo.konio.networking;

import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.networking.connection.ClientConnection;

import java.util.List;
import java.util.Map;

public interface Channel {
    List<ClientConnection> getClients();
    String getChannelName();
    void addMessage(MessageData messageData);
    void subscribeClient(ClientConnection clientConnection);
    void unsubscribeClient(ClientConnection clientConnection);
}
