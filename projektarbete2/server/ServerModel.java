package server;

import message.Packet;
import user.ClientUserName;
import message.Packet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;
import java.awt.*;


//Receives the messages from each client and stores all the messages in an arraylist and sends them to the clients.

public class ServerModel{

    //stores all chat history and User history
    private static List<Packet> messages = new ArrayList<Packet>();
    private static List<ClientUserName> onlineUsers = new ArrayList<ClientUserName>(); // -------------------------------------

    //accepts client connection
    private ServerSocket serverSocket;
    private final int PORT = 1234;

    public ServerModel(){
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
        }
    }

    // getters for chatlog And OnlineUsers
    public static ArrayList<Packet> getChatLog() {return new ArrayList<>(messages);} //-------------------------
    public static ArrayList<ClientUserName> getOnlineUsers() {return new ArrayList<>(onlineUsers);} // ----------------

    public static void addMsg(Packet msg) {messages.add(msg);}
    public static void removeMsg(Packet msg) {messages.remove(msg);}

    public static void addUser(ClientUserName user) {onlineUsers.add(user);}
    public static void removeUser(ClientUserName user) {onlineUsers.remove(user);}


    //Get port and Ip address
    public String getPort(){
        return Integer.toString(PORT);
    }

    public String getIpAddress() {
        String IP;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            IP = ip.getHostAddress();
        } catch (UnknownHostException e){
            IP = "Unknown host exception";
        }
        return IP;
    }

    // Server shutdown Message method ---------------------------------------------------------------
    public void serverShutDown() {
        ClientHandler.serverbroadcastMessage(getChatLog(), new Packet("Server Has Been Shutdown"));
    }

    public void serverMessagesTest() {
        ClientHandler.serverbroadcastMessage(getChatLog(), new Packet ("Load Succesful"));
    }



    //instead of "open", it should say "save log"
    public void save() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser jfc = new JFileChooser(userDirLocation);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                FileOutputStream fileOut = new FileOutputStream(selectedFile);
                ObjectOutputStream output = new ObjectOutputStream(fileOut);
                output.writeObject(messages);
                System.out.println("Save successful");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void load() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser jfc = new JFileChooser(userDirLocation);
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            try {
                FileInputStream fileIn = new FileInputStream(selectedFile);
                ObjectInputStream input = new ObjectInputStream(fileIn);
                this.messages = (ArrayList<Packet>) input.readObject();
                System.out.println("Load successful");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




    //Send online list
    public void sendOnlineList() {ClientHandler.broadcastOnlineClients(getOnlineUsers());}

    public void startServer(){
        //always listen for new client connections
        while (true) {
            try {
                Socket client = serverSocket.accept();
                ClientHandler handler = new ClientHandler(client); //handles the connection between the server and that specific client
                Thread clientHandler = new Thread(handler);
                clientHandler.start();
            } catch(IOException e){
                //do nothing
            }
        }
    }
}
