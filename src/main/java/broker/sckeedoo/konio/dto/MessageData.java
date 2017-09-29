package broker.sckeedoo.konio.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MessageData<T extends Message> implements Serializable {
    private static final long serialVersionUID = -7303201840600026558L;

    private T data;
    private MessageType messageType;
    private List<String> receivers;
    private List<String> channels;

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

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(String... strings) {
        this.channels = Arrays.stream(strings).collect(Collectors.toList());
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }
}
