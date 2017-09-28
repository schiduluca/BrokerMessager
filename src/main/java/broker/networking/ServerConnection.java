package broker.networking;


import broker.commons.OnReceiveListener;
import broker.dto.MessageData;

import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends SimpleConnection {
    private OnReceiveListener onReceiveListener;

    public ServerConnection(Socket socket) {
        super(socket);
        new Thread(listenForMessages()).start();
    }


    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }

    public Runnable listenForMessages() {

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
}
