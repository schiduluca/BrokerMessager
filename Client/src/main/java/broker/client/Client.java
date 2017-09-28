package broker.client;


import broker.commons.OnReceiveListener;
import broker.dto.CustomMessage;
import broker.dto.MessageData;
import broker.dto.MessageType;
import broker.factories.ConnectionFactory;
import broker.networking.ServerConnection;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {


        ConnectionFactory connectionFactory = new ConnectionFactory("Luca");
        ServerConnection connection = connectionFactory.getConnection();



        connection.setOnReceiveListener(new OnReceiveListener() {
            @Override
            public void onSuccess(MessageData message) {
                System.out.println(message.getData().getData());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });



        MessageData<CustomMessage> messageData = new MessageData<>();

        messageData.setData(new CustomMessage("Jora", 21));
        messageData.setMessageType(MessageType.SIMPLE_MESSAGE);

        connection.write(messageData, "Ion", "Vanea");

        connection.createChannel("PLEA");
        connection.createChannel("UTM");
        connection.createChannel("PLEA");


    }
}
