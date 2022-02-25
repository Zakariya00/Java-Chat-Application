package client;

import message.Packet;
import user.ClientUserName; // ----------------------------------------------------------------------------------------

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


//the Client handles the input and output streams. It stores the messages to send.

public class ClientModel {

    private String username;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ArrayList<String> chatLog;


    public ClientModel() {

        try {

            socket = new Socket("localhost",1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e){
            closeConnection();
        }

    }

    public ArrayList<String> getChatLog(){
        return new ArrayList<>(chatLog);
    }



    //skickar ett meddelande till servern med timestamp på meddelandet
    public void sendMessage(String message) {
        String time;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        time = "<"+dtf.format(now)+"> ";

        try {
            objectOutputStream.writeObject(new Packet(time+message));
            objectOutputStream.flush();
        } catch (IOException e){
            //do nothing
        }
    }


    //Send userName to Server ------------------------------------------------------------------------------------------
    public void sendUserName() {
        try {
            objectOutputStream.writeObject(new ClientUserName(getUsername()));
            objectOutputStream.flush();
        } catch (IOException e){
            //do nothing
        }

    }


    // GET and SET Methods for UserName --------------------------------------------------------------------------------
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
            //do nothing
        }
    }


}

















