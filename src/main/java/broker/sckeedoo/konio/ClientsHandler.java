package broker.sckeedoo.konio;

import broker.sckeedoo.konio.commons.db.DatabaseHelper;
import broker.sckeedoo.konio.concurrency.TaskScheduler;
import broker.sckeedoo.konio.concurrency.TaskSchedulerImpl;
import broker.sckeedoo.konio.dto.MessageData;
import broker.sckeedoo.konio.networking.BrokerChannel;
import broker.sckeedoo.konio.networking.connection.ClientConnection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ClientsHandler {
    private List<ClientConnection> allClients = new ArrayList<>();
    private List<BrokerChannel> brokerChannels = new ArrayList<>();
    private Queue<MessageData> linkedQueue = new ConcurrentLinkedQueue<>();
    private static final Logger logger = Logger.getLogger(ClientsHandler.class.getName());
    private TaskScheduler taskScheduler;

    public ClientsHandler() {
        taskScheduler = new TaskSchedulerImpl();
    }

    public void addClient(ClientConnection clientConnection) {
        taskScheduler.submitTask(clientConnection.getConnectionName(), () -> checkForIncomingMessages(clientConnection));
    }


    private void checkForIncomingMessages(ClientConnection clientConnection) {
        while (true) {
            try {
                MessageData o = (MessageData) clientConnection.getObjectInputStream().readObject();
                switch (o.getMessageType()) {
                    case INITIALIZATION:
                        clientConnection.setConnectionName(o.getData());
                        allClients.add(clientConnection);
                        logger.info("Client connected: " + clientConnection.getConnectionName());
                        logger.info("Number of allClients connected: " + allClients.size());
                        break;
                    case SIMPLE_MESSAGE:
                        DatabaseHelper.getINSTANCE().save(o);
                        linkedQueue.add(o);
                        dispatchMessage(o);
                        break;
                    case CREATE_CHANNEL_REQUEST:
                        createChannel(o.getData());
                        break;
                    case CLOSE_CONNECTION:
                        logger.info("Client disconnected");
                        handleDisconnection(clientConnection);
                        taskScheduler.unsubmitTask(clientConnection.getConnectionName());
                        return;
                    case READ_MESSAGE_FROM_QUEUE:
                        MessageData poll = linkedQueue.poll();
                        clientConnection.sendToClient(poll);
                        break;
                    default:
                        break;
                }

            } catch (IOException | ClassNotFoundException e) {
                logger.info("Abnormal disconnection");
                handleDisconnection(clientConnection);
                return;
            }
        }
    }


    private void handleDisconnection(ClientConnection clientConnection) {
        clientConnection.closeConnection();
        logger.info("Client disconnected: " + clientConnection.getConnectionName());
        allClients.remove(clientConnection);
        logger.info("Number of allClients connected: " + allClients.size());
    }

    private void dispatchMessage(MessageData o) {
        allClients.stream()
                .filter(name -> o.getReceivers().contains(name.getConnectionName()))
                .forEach(clientConnection -> clientConnection.sendToClient(o));
    }


    private void createChannel(String channelName) {
        if(brokerChannels.stream().noneMatch(channel -> channel.getChannelName().equals(channelName))) {
            brokerChannels.add(new BrokerChannel(channelName));
            logger.info("Channel with name " + channelName + " created");
        } else {
            logger.info("Channel " + channelName + " already exists.");
        }
    }
}
