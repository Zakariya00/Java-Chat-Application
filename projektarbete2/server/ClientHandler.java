package server;

import message.Packet;
import user.ClientUserName; // -----------------------------------------------------------------------------------------

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//handles 1 client

public class ClientHandler implements Runnable {

    private String username; // client instance being handled's userName -----------------------------------------------
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
                ServerModel.removeUser(username); // User removed from Online users list -----------------------
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
    private static void broadcastFunction(ArrayList<String> obj) {
        for (ClientHandler clientHandler : clientHandlers){
            try {
                clientHandler.objectOutputStream.writeObject(obj);
            } catch(IOException e){
                //do nothing
            }
        }
    }

    //Send Chat messages to clients ---------------------------------------------------
    public void broadcastMessage(ArrayList<String> chat) {
        broadcastFunction(chat);
    }


    //Server custom message broadcast ----------------------------------------------------------------------------------------
    public static void serverbroadcastMessage(ArrayList<String> chat, String msg) {
        chat.add(msg);
        broadcastFunction(chat);
    }

    //Read And Set Client userName -------------------------------
    private void getSetUsername () {
        try {
            ClientUserName userClient = (ClientUserName) objectInputStream.readObject();
            System.out.println("ClientUSERNAME: " + userClient.userName);
            this.username = userClient.userName;
            ServerModel.addUser(userClient.userName); // ----------
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        getSetUsername();

        while (client.isConnected()) {

            try {

                //read message from 1 client and stores it in the arraylist.
                Packet msgFromClient = (Packet) objectInputStream.readObject();
                System.out.println("Server received from client: " + msgFromClient.message);

                //write to all the clients GUI. How can this be accomplished?
                ServerModel.addMsg(msgFromClient.message); // -----------
                System.out.println(ServerModel.getChatLog()); // ----------------

                //creating a copy of messages is necessary because if we write messages, it will not write the entire arraylist.
                ArrayList<String> messagesCopy = new ArrayList<String>(ServerModel.getChatLog()); // --------

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
