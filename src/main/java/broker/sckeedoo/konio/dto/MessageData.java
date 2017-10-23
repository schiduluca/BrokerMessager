package broker.sckeedoo.konio.dto;

import com.mongodb.BasicDBObject;


import java.io.Serializable;
import java.util.List;
public class MessageData extends BasicDBObject implements Serializable {
    private static final long serialVersionUID = -7303201840600026558L;

    private String id;
    private String data;
    private MessageType messageType;
    private List<String> receivers;
    private String channel;

    public MessageData() {
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public List<String> getReceivers() {
        return this.receivers;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
