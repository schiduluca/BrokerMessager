package broker.sckeedoo.konio.client;


import broker.sckeedoo.konio.commons.OnReceiveListener;
import broker.sckeedoo.konio.commons.factories.ConnectionFactory;
import broker.sckeedoo.konio.dto.messagetype.CustomMessage;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.dto.MessageType;
import broker.sckeedoo.konio.networking.connection.ServerConnection;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {

        ServerConnection connection = ConnectionFactory.getINSTANCE()
                .build("Luca")
                .getConnection();


        connection.setOnReceiveListener(new OnReceiveListener() {
            @Override
            public void onSuccess(MessageData message) {
                System.out.println(message.getData().getMessage());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });


        connection.createChannel("PLEA");
        connection.createChannel("UTM");
        connection.createChannel("PLEA");
        connection.subscribeToChannels("UTM");

        MessageData<CustomMessage> messageData = new MessageData<>();

        messageData.setData(new CustomMessage("Jora"));
        messageData.setMessageType(MessageType.SIMPLE_MESSAGE);
        messageData.setChannels("UTM");

        connection.write(messageData, "Ion", "Jora");

    }
}
