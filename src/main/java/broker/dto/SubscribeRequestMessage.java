package broker.dto;

import java.util.List;

public class SubscribeRequestMessage implements Message {

    private List<String> channelList;

    public SubscribeRequestMessage(List<String> channelList) {
        this.channelList = channelList;
    }

    @Override
    public String getData() {
        return null;
    }

    @Override
    public Class getClassType() {
        return SubscribeRequestMessage.class;
    }
}
