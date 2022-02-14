package server;

import message.Packet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//handles 1 client

public class ClientHandler implements Runnable {

    private Socket client;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    //allows the server to send messages to multiple clients. Each connection handler handles one client.
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

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


    public void closeConnection() {
        try {
            if (client != null) {
                client.close();
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

    public void broadcastMessage(ArrayList<String> chat) {
        for (ClientHandler clientHandler : clientHandlers){
            try {
                clientHandler.objectOutputStream.writeObject(chat);
            } catch(IOException e){
                //do nothing
            }
        }
    }


    @Override
    public void run() {


        while (client.isConnected()) {

            try {


                //read message from 1 client and stores it in the arraylist.
                Packet msgFromClient = (Packet) objectInputStream.readObject();
                System.out.println("Server received from client: " + msgFromClient.message);

                //write to all the clients GUI. How can this be accomplished?
                ServerModel.messages.add(msgFromClient.message);
                System.out.println(ServerModel.messages);

                //creating a copy of messages is necessary because if we write messages, it will not write the entire arraylist.
                ArrayList<String> messagesCopy = new ArrayList<String>(ServerModel.messages);

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