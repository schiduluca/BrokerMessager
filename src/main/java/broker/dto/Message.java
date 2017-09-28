package broker.dto;

import java.io.Serializable;

public interface Message extends Serializable {
    String getData();
    Class getClassType();

}
