package broker.sckeedoo.konio.dto.messagetype;

import broker.sckeedoo.konio.dto.Message;

public class ChannelRequestMessage implements Message {

    private String channelName;

    public ChannelRequestMessage(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public String getMessage() {
        return this.channelName;
    }

    @Override
    public Class getClassType() {
        return ChannelRequestMessage.class;
    }

}
