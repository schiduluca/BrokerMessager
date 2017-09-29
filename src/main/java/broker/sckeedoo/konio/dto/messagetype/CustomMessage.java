package broker.sckeedoo.konio.dto.messagetype;

import broker.sckeedoo.konio.dto.Message;

public class CustomMessage implements Message {

    private static final long serialVersionUID = -3706310678806165793L;

    private String message;

    public CustomMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Class getClassType() {
        return CustomMessage.class;
    }


}
