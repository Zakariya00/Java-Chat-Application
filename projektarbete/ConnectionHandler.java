import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable {

    private Socket client;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;


    public ConnectionHandler(Socket client){
        this.client = client;
    }


    @Override
    public void run() {
        try {

            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());

            while (true){


                //read message from 1 client and stores it in the arraylist.
                Packet msgFromClient = (Packet) objectInputStream.readObject();
                System.out.println("Server received from client: " +msgFromClient.message);

                //write to all the clients GUI. How can this be accomplished?
                ServerModel.messages.add(msgFromClient.message);
                System.out.println(ServerModel.messages);

                //creating a copy of messages is necessary because if we write messages, it will not write the entire arraylist.
                ArrayList<String> messagesCopy = new ArrayList<String>(ServerModel.messages);

                //sends the arraylist to all the clients. We also want all clients objectInputStream to read it and display it in the GUI.
                objectOutputStream.writeObject(messagesCopy);


            }



        }catch (IOException | ClassNotFoundException e){
            //do nothing
        } finally {

            //connections.remove(this);
            //closeConnectionHandler();

        }
    }

}