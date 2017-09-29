package broker.sckeedoo.konio.networking.connection;


import broker.sckeedoo.konio.commons.OnReceiveListener;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.dto.MessageType;
import broker.sckeedoo.konio.dto.messagetype.ChannelRequestMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerConnection extends SimpleConnection {
    private Map<String, OnReceiveListener> onReceiveListeners = new ConcurrentHashMap<>();

    public ServerConnection(Socket socket) {
        super(socket);
        new Thread(listenForMessages()).start();
    }


    public void subscribeToChannel(String channelName, OnReceiveListener onReceiveListener) {
        this.onReceiveListeners.put(channelName, onReceiveListener);
    }

    private Runnable listenForMessages() {

        return () -> {
            while (!getSocket().isClosed()) {
                try {
                    MessageData message = (MessageData) getObjectInputStream().readObject();
                    onReceiveListeners.forEach((key, value) -> {
                        if(message.getChannel().equals(key)) {
                            value.onSuccess(message);
                        }
                    });
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Server connection interrupted.");
                    try {
                        getSocket().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
    }

    public void createChannel(String channelName) {
        MessageData<ChannelRequestMessage> channelRequest = new MessageData<>();
        channelRequest.setMessageType(MessageType.CREATE_CHANNEL_REQUEST);
        channelRequest.setData(new ChannelRequestMessage(channelName));
        write(channelRequest);
    }

}
