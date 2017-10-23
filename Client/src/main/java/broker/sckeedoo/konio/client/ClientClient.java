package broker.sckeedoo.konio.client;

import broker.sckeedoo.konio.commons.OnReceiveListener;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.commons.factories.ConnectionFactory;
import broker.sckeedoo.konio.dto.converter.GsonConverter;
import broker.sckeedoo.konio.dto.entities.User;
import broker.sckeedoo.konio.networking.connection.ServerConnection;

import java.util.Scanner;
import java.util.UUID;

public class ClientClient {

    public static void main(String[] args) {

        ServerConnection connection = ConnectionFactory.getINSTANCE()
                .build("Jora", 1234)
                .getConnection();



        connection.createChannel("UTM");

        connection.subscribeToChannel("UTM", new OnReceiveListener() {
            @Override
            public void onSuccess(MessageData message) {
                User user = GsonConverter.convertToDto(message.getData(), User.class);

                System.out.println(user.getAge() + " " + user.getName());
            }

            @Override
            public void onFailure(Throwable throwable) {
                // handle
            }
        });


        connection.readFromChannel();
        Scanner scanner = new Scanner(System.in);
        while (true) {
//            String string = scanner.nextLine();
//            connection.write();
        }

    }
}
