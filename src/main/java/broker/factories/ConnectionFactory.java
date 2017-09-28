package broker.factories;



import broker.dto.InitializationMessage;
import broker.dto.MessageData;
import broker.dto.MessageType;
import broker.networking.ServerConnection;

import java.io.IOException;
import java.net.Socket;

public class ConnectionFactory {
    private String hostName;
    private ServerConnection connection;
    private Socket socket;

    public ConnectionFactory(String hostName) {
        try {
            socket = new Socket("localhost", 1234);
            System.out.println(socket.getInetAddress() + " " + socket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }

        MessageData<InitializationMessage> initMessage = new MessageData<>();
        initMessage.setMessageType(MessageType.INITIALIZATION);
        initMessage.setData(new InitializationMessage(hostName));
        connection = new ServerConnection(socket);
        connection.write(initMessage);
    }

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
