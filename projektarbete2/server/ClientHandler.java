package server;

import message.Message;
import user.ClientUserName; // -----------------------------------------------------------------------------------------

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler implements Runnable {

    private ClientUserName username; // client instance being handled's userName -----------------------------------------------
    private Socket client;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    //allows the server to send messages to multiple clients. Each connection handler handles one client.
    private static List<ClientHandler> clientHandlers = new ArrayList<>();


    public ClientHandler(Socket client) {
        try {
            this.client = client;
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectInputStream = new ObjectInputStream(client.getInputStream());
            clientHandlers.add(this);

        } catch (IOException e) {
            //do nothing
        }
    }


    // returns client connections
    public ArrayList<ClientHandler> getClientConnections() {return new ArrayList<>(clientHandlers);}


    public void closeConnection() {
        try {
            if (client != null) {
                client.close();
                ServerModel.removeUser(username);// User removed from Online users list ---------------
                broadcastOnlineClients(ServerModel.getOnlineUsers());
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (IOException e) {
            //
        }
    }

    //BroadCast function to send messages  --------------------------------------
    private static void broadcastFunction(ArrayList<Message> obj) {
        for (ClientHandler clientHandler : clientHandlers){
            try {
                clientHandler.objectOutputStream.writeObject(obj);
                clientHandler.objectOutputStream.flush();
            } catch(IOException e){
                //do nothing
            }
        }

    }

    //Send Chat messages to clients ---------------------------------------------------
    public static void broadcastMessage(ArrayList<Message> chat) {
        broadcastFunction(chat);
    }

    //Server custom message broadcast ----------------------------------------------------
    public static void serverbroadcastMessage(ArrayList<Message> chat, Message msg) {
        chat.add(msg);
        broadcastFunction(chat);
    }

    //Send Online List to Clients -----------------------------------------------------
    public static void broadcastOnlineClients(ArrayList<ClientUserName> obj) {
        for (ClientHandler clientHandler : clientHandlers){
            try {
                clientHandler.objectOutputStream.writeObject(obj);
                clientHandler.objectOutputStream.flush();
            } catch(Exception e){
                //do nothing
                System.out.println("<BroadCasting Client List Failure!>\n");
                e.printStackTrace();
            }
        }
    }

    //Read And Set Client userName -------------------------------
    private void getSetUsername () {
        try {
            ClientUserName userClient = (ClientUserName) objectInputStream.readObject();
            System.out.println("ClientUSERNAME: " + userClient.toString());
            this.username = userClient;
            ServerModel.addUser(userClient); // ----------
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        getSetUsername();
        if (ServerModel.getOnlineUsers().size() != 0){broadcastOnlineClients(ServerModel.getOnlineUsers());}

        while (client.isConnected()) {

            try {

                //read message from 1 client and stores it in the arraylist.
                Message msgFromClient = (Message) objectInputStream.readObject();
                System.out.println("Server received from client: " + msgFromClient.toString());

                //write to all the clients GUI. How can this be accomplished?
                ServerModel.addMsg(msgFromClient); // -----------
                System.out.println(ServerModel.getChatLog()); // ----------------

                //creating a copy of messages is necessary because if we write messages, it will not write the entire arraylist.
                ArrayList<Message> messagesCopy = new ArrayList<Message>(ServerModel.getChatLog()); // --------

                //sends the arraylist to all the clients. We also want all clients objectInputStream to read it and display it in the GUI.
                broadcastMessage(messagesCopy);


            } catch (IOException | ClassNotFoundException e) {
                //do nothing
                closeConnection();
                break;
            }
        }
    }


}
