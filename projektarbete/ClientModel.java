import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

//the Client handles the input and output streams. It stores the messages to send.

public class ClientModel implements Runnable
{


    private String username;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ArrayList<String> chatLog;



    public void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(new Packet(message));
            objectOutputStream.flush();
        } catch (IOException e){
            //do nothing
        }
    }



    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public ArrayList<String> readMessage() {
        try {
            chatLog = (ArrayList<String>) objectInputStream.readObject();
            System.out.println(chatLog);

            return chatLog;
        } catch (IOException | ClassNotFoundException e){
            //do nothing
        }
        return new ArrayList<String>();
    }

    public void closeConnection() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        try {

            socket = new Socket("localhost",1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e){
            closeConnection();
        }

    }




}















