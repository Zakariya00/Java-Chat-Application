package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


//Receives the messages from each client and stores all the messages in an arraylist and sends them to the clients.

public class ServerModel{

    //stores all chat history and User history
    private static List<String> messages = new ArrayList<String>();
    private static List<String> onlineUsers = new ArrayList<String>(); // -------------------------------------

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
    public static ArrayList<String> getChatLog() {return new ArrayList<>(messages);} //-------------------------
    public static ArrayList<String> getOnlineUsers() {return new ArrayList<>(onlineUsers);} // ----------------

    public static void addMsg(String msg) {messages.add(msg);}
    public static void removeMsg(String msg) {messages.remove(msg);}

    public static void addUser(String user) {onlineUsers.add(user);}
    public static void removeUser(String user) {onlineUsers.remove(user);}


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

    // Server shutdown Message method ---------------------------------------------------------------------------------
    public void serverShutDown() {
        ClientHandler.serverbroadcastMessage(getChatLog(), "Server Has Been Shutdown");
    }

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
