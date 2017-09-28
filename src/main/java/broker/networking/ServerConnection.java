package broker.networking;


import broker.commons.OnReceiveListener;
import broker.dto.ChannelRequestMessage;
import broker.dto.InitializationMessage;
import broker.dto.MessageData;
import broker.dto.MessageType;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

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
}
