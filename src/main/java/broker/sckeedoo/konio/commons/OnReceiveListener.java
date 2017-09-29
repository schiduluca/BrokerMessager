package broker.sckeedoo.konio.commons;


import broker.sckeedoo.konio.dto.MessageData;

public interface OnReceiveListener {
    void onSuccess(MessageData<?> message);

    void onFailure(Throwable throwable);
}
