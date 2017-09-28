package broker.commons;


import broker.dto.MessageData;

public interface OnReceiveListener {
    void onSuccess(MessageData message);

    void onFailure(Throwable throwable);
}
