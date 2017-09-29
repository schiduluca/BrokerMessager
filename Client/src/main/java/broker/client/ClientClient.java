package broker.client;

import broker.commons.OnReceiveListener;
import broker.dto.MessageData;
import broker.factories.ConnectionFactory;
import broker.networking.ServerConnection;

import java.util.Scanner;

public class ClientClient {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory("Vanea");
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

        Scanner scanner = new Scanner(System.in);
        while (true) {
//            String string = scanner.nextLine();
//            connection.write();
        }

    }
}
