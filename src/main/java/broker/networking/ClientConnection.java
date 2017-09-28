package broker.networking;


import broker.dto.MessageData;

import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends SimpleConnection {
    private String connectionName;

    public ClientConnection(Socket socket) {
        super(socket);
    }

    public void sendToClient(MessageData message) {
        try {
            geObjectOutputStream().writeObject(message);
            geObjectOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }
}
