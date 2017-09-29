package broker.sckeedoo.konio.client;


import broker.sckeedoo.konio.commons.OnReceiveListener;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.commons.factories.ConnectionFactory;
import broker.sckeedoo.konio.networking.connection.ServerConnection;

public class ClientTwo {

    public static void main(String[] args) {

        ServerConnection connection = ConnectionFactory.getINSTANCE()
                .build("Ion")
                .getConnection();

        connection.subscribeToChannels("UTM");

        connection.setOnReceiveListener(new OnReceiveListener() {
            @Override
            public void onSuccess(MessageData message) {
                System.out.println(message);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
