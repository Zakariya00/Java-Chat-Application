package server;

import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains the logic for communicating with the client over the objectinputstream and objectoutputstream.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 * @version 1.0 3/2/2022
 */
public class ClientHandler implements Runnable {

    private static final List<ClientHandler> clientHandlers = new ArrayList<>();
    private String username;
    private Socket client;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    /**
     * class constructor opens an objectStream to be used
     *
     * @param client initialise the client socket
     */
    public ClientHandler(Socket client) {
        try {

            this.client = client;
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());
            clientHandlers.add(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * methods broadcasts to all clients through the outputstream
     *
     * @param obj object to be sent to the output stream
     */
    private static void broadcastFunction(ArrayList<Message> obj) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.objectOutputStream.writeObject(obj);
                clientHandler.objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends chat object to the clients
     *
     * @param chat object to be broadcasted
     */
    public static void broadcastMessage(ArrayList<Message> chat) {
        broadcastFunction(chat);
    }

    /**
     * sends message to the Server GUiI
     *
     * @param chat array for saving the chat
     * @param msg  meassage to be broadcasted
     */
    public static void serverbroadcastMessage(ArrayList<Message> chat, Message msg) {
        chat.add(msg);
        broadcastFunction(chat);
    }

    /**
     * sends a list of online users to clients.
     *
     * @param obj list of clients online
     */
    public static void broadcastOnlineClients(ArrayList<String> obj) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.objectOutputStream.writeObject(obj);
                clientHandler.objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * used for getting the list of users connectd to the server
     *
     * @return list of clients connected to the server
     */
    public ArrayList<ClientHandler> getClientConnections() {
        return new ArrayList<>(clientHandlers);
    }

    /**
     * closes server connection incase of an error or user disconnects
     * close all sockets and streams to free resources
     */
    public void closeConnection() {
        try {
            if (client != null) {
                client.close();
                ServerModel.removeUser(username);
                broadcastOnlineClients(ServerModel.getOnlineUsers());
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads and sets client username
     */
    private void getSetUsername() {
        try {
            String userClient = (String) objectInputStream.readObject();
            this.username = userClient;
            ServerModel.addUser(userClient);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main run method for the server that
     * enters into a continous loop to read the incoming stream ounce the client is connected.
     * Its broadcasts the in stream to all the clients.
     */
    @Override
    public void run() {
        getSetUsername();
        if (ServerModel.getOnlineUsers().size() != 0) {
            broadcastOnlineClients(ServerModel.getOnlineUsers());
        }

        while (client.isConnected()) {

            try {
                Message msgFromClient = (Message) objectInputStream.readObject();
                ServerModel.addMsg(msgFromClient);

                ArrayList<Message> messagesCopy = new ArrayList<Message>(ServerModel.getChatLog());
                broadcastMessage(messagesCopy);

            } catch (IOException | ClassNotFoundException e) {
                closeConnection();
                break;
            }
        }
    }

}
