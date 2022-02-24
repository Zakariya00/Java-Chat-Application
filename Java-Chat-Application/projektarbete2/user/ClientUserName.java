package user;

import java.io.Serializable;


public class ClientUserName implements Serializable {

    public String userName;

    public ClientUserName(String user){
        this.userName = user;
    }

}
