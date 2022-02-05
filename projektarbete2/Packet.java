import java.io.Serializable;

//every message we send is a packet because objectoutputstream can not handle strings. it can only handle serialized objects. thats why packet implements serializable.

public class Packet implements Serializable {

    public String message;

    public Packet(String message){
        this.message = message;
    }


}
