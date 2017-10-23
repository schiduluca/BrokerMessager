package broker.sckeedoo.konio.commons.factories;

import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.dto.MessageType;
import broker.sckeedoo.konio.networking.connection.ServerConnection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ConnectionFactory {
    private static ConnectionFactory INSTANCE;
    private String hostName;
    private ServerConnection connection;
    private Socket socket;
    private int port;

    public static ConnectionFactory getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = new ConnectionFactory();
        }
        return INSTANCE;
    }

    public ConnectionFactory build(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        createConnection();

        return this;
    }


    private void createConnection() {
        try {
            socket = new Socket("localhost", port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessageData initMessage = new MessageData();
        initMessage.setMessageType(MessageType.INITIALIZATION);
        initMessage.setData(hostName);
        connection = new ServerConnection(socket);
        connection.write(initMessage);
    }

    private ConnectionFactory() {}

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public ServerConnection getConnection() {
        return connection;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
