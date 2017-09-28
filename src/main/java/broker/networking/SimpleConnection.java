package broker.networking;


import broker.dto.MessageData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public abstract class SimpleConnection {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public SimpleConnection(Socket socket) {
        try {
            this.socket = socket;
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(MessageData messageData, String... receivers) {
        try {
            messageData.setReceivers(Arrays.asList(receivers));
            objectOutputStream.writeObject(messageData);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            objectInputStream.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ObjectInputStream getObjectInputStream() {
        return this.objectInputStream;
    }

    public ObjectOutputStream geObjectOutputStream() {
        return this.objectOutputStream;
    }

    public Socket getSocket() {
        return socket;
    }

}
