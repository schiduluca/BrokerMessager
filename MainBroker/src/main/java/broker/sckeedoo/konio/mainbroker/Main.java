package broker.sckeedoo.konio.mainbroker;


import broker.sckeedoo.konio.BrokerMQ;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

    static Properties properties = new Properties();

    public static void main(String[] args) {

        BrokerMQ mainBroker = new BrokerMQ(1234);
        mainBroker.startBroker();


    }
}
