package server;

import message.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.io.*;


//Receives the messages from each client and stores all the messages in an arraylist and sends them to the clients.

/**
 * Contains the logic for creating the server socket and handling the chatlog.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 * @version 1.0 3/2/2022
 */
public class ServerModel {

    private static final List<Message> messages = new ArrayList<Message>();
    private static final List<String> onlineUsers = new ArrayList<>();
    private final int PORT = 1234;
    private ServerSocket serverSocket;

    /**
     * class constructor for creating serversocket
     */
    public ServerModel() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * used for getting the chatlog
     *
     * @return messages
     */
    public static ArrayList<Message> getChatLog() {
        return new ArrayList<>(messages);
    }

    /**
     * used for getting the list of online users
     *
     * @return online users
     */
    public static ArrayList<String> getOnlineUsers() {
        return new ArrayList<>(onlineUsers);
    }

    /**
     * used for storing the messages in an array
     *
     * @param msg message to be added stored
     */
    public static void addMsg(Message msg) {
        messages.add(msg);
    }

    /**
     * method for removing messages from the chatlog
     *
     * @param msg message to be removed
     */
    public static void removeMsg(Message msg) {
        messages.remove(msg);
    }

    /**
     * add a user to the online user list
     *
     * @param user user to be added to the online user list
     */
    public static void addUser(String user) {
        onlineUsers.add(user);
    }

    /**
     * removes a user from the online userlist
     *
     * @param user user to be removed
     */
    public static void removeUser(String user) {
        onlineUsers.remove(user);
    }


    /**
     * gets the port number of the server
     *
     * @return port number
     */
    public String getPort() {
        return Integer.toString(PORT);
    }

    /**
     * gets the IP of the srever
     *
     * @return server IP
     */
    public String getIpAddress() {
        String IP;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            IP = ip.getHostAddress();
        } catch (UnknownHostException e) {
            IP = "Unknown host exception";
        }
        return IP;
    }

    /**
     * sends messeges to the clients when the server shuts down
     */
    public void serverShutDown() {
        ClientHandler.serverbroadcastMessage(getChatLog(), new Message("Server Has Been Shutdown"));
    }

    /**
     * sends chat messeges to the clients
     */
    public void sendMessages() {
        ClientHandler.broadcastMessage(getChatLog());
    }

    /**
     * saves the current chatlog to a file
     */
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * reads saved chat history from the file
     */
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

                List<Message> tmp = new ArrayList<Message>();
                tmp = (ArrayList<Message>) input.readObject();
                messages.addAll(0, tmp);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * sends online user list to the clients
     */
    public void sendOnlineList() {
        ClientHandler.broadcastOnlineClients(getOnlineUsers());
    }

    /**
     * Continously listens for new client connectionsand creates new threads for the client.
     */
    public void startServer() {
        //always listen for new client connections
        while (true) {
            try {
                Socket client = serverSocket.accept();
                ClientHandler handler = new ClientHandler(client);
                Thread clientHandler = new Thread(handler);
                clientHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
