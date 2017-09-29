package broker.sckeedoo.konio.client;

import broker.sckeedoo.konio.commons.OnReceiveListener;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.commons.factories.ConnectionFactory;
import broker.sckeedoo.konio.networking.connection.ServerConnection;

import java.util.Scanner;

public class ClientClient {

    public static void main(String[] args) {

        ServerConnection connection = ConnectionFactory.getINSTANCE()
                .build("Jora")
                .getConnection();


        connection.subscribeToChannel("PLEA", new OnReceiveListener() {
            @Override
            public void onSuccess(MessageData<?> message) {
                System.out.println(message.getData());
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
