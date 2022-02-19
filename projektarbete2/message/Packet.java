package message;

import java.io.Serializable;

// Every message we send is a packet because objectoutputstream can not handle strings. It can only handle serialized objects.
// That's why packet implements serializable.

public class Packet implements Serializable {

    public String message;

    public Packet(String message){
        this.message = message;
    }


}
