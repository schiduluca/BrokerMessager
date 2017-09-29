package broker.sckeedoo.konio.dto.messagetype;

import broker.sckeedoo.konio.dto.Message;

public class InitializationMessage implements Message {
    private static final long serialVersionUID = -1024359696043965764L;

    private String hostName;

    public InitializationMessage(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String getMessage() {
        return this.hostName;
    }

    @Override
    public Class getClassType() {
        return InitializationMessage.class;
    }



}
