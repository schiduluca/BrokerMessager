package broker.sckeedoo.konio.dto;

import java.io.Serializable;

public interface Message extends Serializable {
    String getMessage();
    Class getClassType();

}
