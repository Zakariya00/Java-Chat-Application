package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

//Receives the messages from each client and stores all the messages in an arraylist and sends them to the clients.

public class ServerModel{

    //stores all chat history
    public static ArrayList<String> messages = new ArrayList<String>();

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


    public String getPort(){
        return Integer.toString(PORT);
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