package broker.dto;

public class ChannelRequestMessage implements Message {

    private String channelName;

    public ChannelRequestMessage(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public String getData() {
        return this.channelName;
    }

    @Override
    public Class getClassType() {
        return ChannelRequestMessage.class;
    }

}
