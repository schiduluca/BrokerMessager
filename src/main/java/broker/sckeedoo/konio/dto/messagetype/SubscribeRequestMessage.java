package broker.sckeedoo.konio.dto.messagetype;

import broker.sckeedoo.konio.dto.Message;

import java.util.List;

public class SubscribeRequestMessage implements Message {

    private List<String> channelList;

    public SubscribeRequestMessage(List<String> channelList) {
        this.channelList = channelList;
    }

    @Override
    public String getMessage() {
        return channelList.stream().reduce((s, s2) -> s + "," + s2).orElse("");
    }

    @Override
    public Class getClassType() {
        return SubscribeRequestMessage.class;
    }
}
