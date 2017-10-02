# BrokerMessager
-------

Simple broker messager for Java applications by Schidu Luca.

## Usage
-------
- ### Client

You can get a ``ServerConnection`` via ``ConnectionFactory``. In ``build()`` method you should specify the ``name`` of your client.

```java
ServerConnection connection = ConnectionFactory.getINSTANCE()
                .build("Luca")
                .getConnection();
```

 You can create a channel in the following way:
 
 ```java
 connection.createChannel("FAF");
 ```
 
 You can subscribe to an existing channel and listen for incoming messages:
 
 ```java
 connection.subscribeToChannel("FAF", new OnReceiveListener() {
            @Override
            public void onSuccess(MessageData message) {
               
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
 
 ```
 
 Sending a message to a specific channel:
 
 ```java
 
MessageData messageData = new MessageData();

// setData() method takes a String variable
messageData.setData(JsonConverter.convertToJson(new User("Luca", 22)));

// INITIALIZATION, SIMPLE_MESSAGE, CREATE_CHANNEL_REQUEST, CLOSE_CONNECTION
messageData.setMessageType(MessageType.SIMPLE_MESSAGE);


// set the channel for this message
messageData.setChannel("FAF");

// write() method takes a MessageData variable and String... args as receivers
connection.write(messageData, "Ion", "Jora");
 
 ```
 ----
 - ### Broker
 
 Start the broker messager:
 
 ```java
 
 BrokerMQ mainBroker = new BrokerMQ();
        mainBroker.startBroker();
        
 ```
