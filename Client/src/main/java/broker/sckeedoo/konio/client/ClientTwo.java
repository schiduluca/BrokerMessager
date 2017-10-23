package broker.sckeedoo.konio.client;


import broker.sckeedoo.konio.commons.OnReceiveListener;
import broker.sckeedoo.konio.commons.factories.ConnectionFactory;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.networking.connection.ServerConnection;

public class ClientTwo {

    public static void main(String[] args) {

        ServerConnection connection = ConnectionFactory.getINSTANCE()
                .build("Ion", 1234)
                .getConnection();

        connection.subscribeToChannel("UTM", new OnReceiveListener() {
            @Override
            public void onSuccess(MessageData message) {
                System.out.println(message.getData());
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

    }
}
