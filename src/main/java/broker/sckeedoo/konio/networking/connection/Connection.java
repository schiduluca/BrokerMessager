package broker.sckeedoo.konio.networking.connection;

import broker.sckeedoo.konio.dto.MessageData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public interface Connection {
    ObjectOutputStream geObjectOutputStream();
    ObjectInputStream getObjectInputStream();
    Socket getSocket();
    void write(MessageData messageData, String... receivers);
    void closeConnection();
}
