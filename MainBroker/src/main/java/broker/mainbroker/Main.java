package broker.mainbroker;


import broker.BrokerMQ;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    static Properties properties = new Properties();

    public static void main(String[] args) {

        try {
            properties.load(new FileReader("MainBroker/src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(properties);
        BrokerMQ mainBroker = new BrokerMQ();
        mainBroker.startBroker();
    }
}
