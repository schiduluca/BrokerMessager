package broker.sckeedoo.konio.dto;

public enum MessageType {
    INITIALIZATION,
    SIMPLE_MESSAGE,
    CREATE_CHANNEL_REQUEST,
    CLOSE_CONNECTION,
    READ_MESSAGE_FROM_QUEUE
}
