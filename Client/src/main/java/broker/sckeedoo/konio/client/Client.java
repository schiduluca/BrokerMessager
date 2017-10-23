package broker.sckeedoo.konio.client;


import broker.sckeedoo.konio.commons.factories.ConnectionFactory;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.dto.MessageType;
import broker.sckeedoo.konio.dto.converter.GsonConverter;
import broker.sckeedoo.konio.dto.entities.User;
import broker.sckeedoo.konio.networking.connection.ServerConnection;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {

        ServerConnection connection = ConnectionFactory.getINSTANCE()
                .build("Luca", 1234)
                .getConnection();


        connection.createChannel("FAF");
        connection.createChannel("UTM");

        MessageData messageData = new MessageData();


        messageData.setData(GsonConverter.convertToJson(new User("Luca", 22)));


        messageData.setMessageType(MessageType.SIMPLE_MESSAGE);
        messageData.setChannel("UTM");

        connection.write(messageData, "Ion", "Jora");

        connection.close();

    }
}
