import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable
{

    private String username;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

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
            ArrayList<String> chatLog = new ArrayList<String>();
            chatLog = (ArrayList<String>) objectInputStream.readObject();
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

        try{
            socket = new Socket("localhost",1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e){
            closeConnection();
        }

    }




}















