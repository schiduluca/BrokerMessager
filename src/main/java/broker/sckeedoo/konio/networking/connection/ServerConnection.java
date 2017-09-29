package broker.sckeedoo.konio.networking.connection;


import broker.sckeedoo.konio.commons.OnReceiveListener;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.dto.MessageType;
import broker.sckeedoo.konio.dto.messagetype.ChannelRequestMessage;
import broker.sckeedoo.konio.dto.messagetype.SubscribeRequestMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ServerConnection extends SimpleConnection {
    private OnReceiveListener onReceiveListener;

    public ServerConnection(Socket socket) {
        super(socket);
        new Thread(listenForMessages()).start();
    }


    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    private Runnable listenForMessages() {

        return () -> {
            while (!getSocket().isClosed()) {
                try {
                    MessageData message = (MessageData) getObjectInputStream().readObject();
                    if(onReceiveListener != null) {
                        onReceiveListener.onSuccess(message);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    onReceiveListener.onFailure(e);
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

    public void subscribeToChannels(String... args) {
        MessageData<SubscribeRequestMessage> subscribeRequest = new MessageData<>();
        subscribeRequest.setMessageType(MessageType.SUBSCRIBE_REQUEST);
        subscribeRequest.setData(new SubscribeRequestMessage(Arrays.stream(args).collect(Collectors.toList())));
        write(subscribeRequest);
    }
}
