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
import java.util.List;


//the Client handles the input and output streams. It stores the messages to send.

public class ClientModel {

    private String username;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private List<Packet> chatLog = new ArrayList<Packet>();
    private List<ClientUserName> onlineUsers = new ArrayList<ClientUserName>(); // ---------


    public ClientModel() {
        try {
            socket = new Socket("localhost", 1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e){
            closeConnection();
        }

    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Get and Set methods for private Instance Variables
    public ArrayList<Packet> getChatLog(){return new ArrayList<>(chatLog); }
    public ArrayList<ClientUserName> getOnlineUsers() {return new ArrayList<>(onlineUsers); }
    public Socket getSocket(){
        return socket;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
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


    public void readMessage() {
        try {
            chatLog = (ArrayList<Packet>) objectInputStream.readObject();
            System.out.println(chatLog.toString());

        } catch (IOException | ClassNotFoundException e){
            //do nothing
        }
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

















