package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


//Receives the messages from each client and stores all the messages in an arraylist and sends them to the clients.

public class ServerModel{

    //stores all chat history
    public static ArrayList<String> messages = new ArrayList<String>();

    //Q: Vad används copy för? Inga publika variabler.
    public static ArrayList<String> messagesCopy = new ArrayList<String>(); // -----------------------------------------

    public static ArrayList<String> onlineUsers = new ArrayList<String>(); // ------------------------------------------
    public static ArrayList<String> onlineUsersCopy = new ArrayList<String>(); // --------------------------------------

    public static ArrayList<String> connectionLog = new ArrayList<String>();


    //accepts client connection
    private ServerSocket serverSocket;
    private final int PORT = 1234;


    public ServerModel(){
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
        }
    }

    // getters for chat and connection log
    public ArrayList<String> getChatLog() {return new ArrayList<>(messages);} //----------------------------------------

    //Q: vad används copy för?
    public ArrayList<String> getChatLogCopy() {return new ArrayList<>(messagesCopy);}

    public ArrayList<String> getOnlineUsers() {return new ArrayList<>(onlineUsers);} // --------------------------------
    public ArrayList<String> getOnlineUsersCopy() {return new ArrayList<>(onlineUsersCopy);}

    public ArrayList<String> getConnectionLog() {return new ArrayList<>(connectionLog);}

    // Setters for Chatlog and OnlineUsersCopy -------------------------------------------------------------------------
    public void updateChatLogCopy () { messagesCopy = getChatLog();}
    public void updateOnlineUsersCopy () {onlineUsersCopy = getOnlineUsers();}


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
        ClientHandler.serverbroadcastMessage(getChatLog());
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
