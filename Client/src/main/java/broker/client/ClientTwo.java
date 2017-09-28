package broker.client;


import broker.commons.OnReceiveListener;
import broker.dto.MessageData;
import broker.factories.ConnectionFactory;
import broker.networking.ServerConnection;

public class ClientTwo {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory("Ion");
        ServerConnection connection = connectionFactory.getConnection();

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
