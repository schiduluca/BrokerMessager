package broker.sckeedoo.konio.commons.db;

import broker.sckeedoo.konio.dto.MessageData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import java.net.UnknownHostException;

import static broker.sckeedoo.konio.commons.db.DatabaseConfigs.*;


public class DatabaseHelper {

    private static DatabaseHelper INSTANCE;
    private Mongo connection;
    private static DBCollection dbCollection;

    private DatabaseHelper() {
        try {
            connection = new Mongo(HOST, PORT);
            dbCollection = connection.getDB(DATABASE).getCollection(COLLECTION);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseHelper getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = new DatabaseHelper();
        }
        return INSTANCE;
    }



    public void save(MessageData messageData) {
        BasicDBObject object = new BasicDBObject();
        object.append(MESSAGE_KEY, messageData.getData())
                .append(USERS_KEY, messageData.getReceivers())
                .append(CHANNEL_KEY, messageData.getChannel());


        dbCollection.save(object);
    }
}
