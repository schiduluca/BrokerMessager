package broker.dto;

import java.io.Serializable;
import java.util.List;

public class MessageData<T extends Message> implements Serializable {
    private static final long serialVersionUID = -7303201840600026558L;

    private T data;
    private MessageType messageType;
    private List<String> receivers;

    public MessageData() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }
}