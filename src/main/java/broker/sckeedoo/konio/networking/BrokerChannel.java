package broker.sckeedoo.konio.networking;

import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.networking.connection.ClientConnection;

import java.util.ArrayList;
import java.util.List;

public class BrokerChannel implements Channel {

    private String channelName;
    private List<ClientConnection> clients = new ArrayList<>();
    private List<MessageData> dataList = new ArrayList<>();

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
    public void addMessage(MessageData messageData) {
        this.dataList.add(messageData);
    }

    @Override
    public void subscribeClient(ClientConnection clientConnection) {
        clients.add(clientConnection);
    }

    @Override
    public void unsubscribeClient(ClientConnection clientConnection) {
        clients.remove(clientConnection);
    }

    public List<MessageData> getDataList() {
        return dataList;
    }

    public void setDataList(List<MessageData> dataList) {
        this.dataList = dataList;
    }


}

