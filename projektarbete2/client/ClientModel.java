package client;

import message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains the logic for communicating with the server over the objectinputstream and objectoutputstream.
 * Stores all the messages that the server sends.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 * @version 1.0 3/2/2022
 */

public class ClientModel {

    private String username;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private List<Message> chatLog = new ArrayList<>();
    private List<String> onlineUsers = new ArrayList<>();
    private boolean sound;


    /**
     * Class constructor that creates the socket that communicates with the server
     * and initializes the objectoutputstream and objectinputstream from that socket.
     *
     * @Exception IOException
     */
    public ClientModel() {
        try {
            socket = new Socket("localhost", 1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }

    }

    /**
     * updates sound notification to true or false
     *
     * @param b for updating sound notification
     */
    public void setsound(boolean b) {
        sound = b;
    }


    /**
     * re-initializes the socket and object streams when the connect button is pressed
     * in case the server was not running when the client started
     *
     * @throws IOException
     */
    public void connectToServer() {
        try {
            socket = new Socket("localhost", 1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }


    /**
     * this method gets the chatlog
     *
     * @return An arraylist of Message
     */
    public ArrayList<Message> getChatLog() {
        return new ArrayList<>(chatLog);
    }


    /**
     * this method gets the names of online users
     *
     * @return an arraylist of onlineusrs
     */
    public ArrayList<String> getOnlineUsers() {
        return new ArrayList<>(onlineUsers);
    }


    /**
     * @return Socket for the client
     */
    public Socket getSocket() {
        return socket;
    }


    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }


    /**
     * Sets the username to the given input
     *
     * @param username the name of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Sends a Message object to the server over the objectoutputstream.
     *
     * @param message The message to send to the server
     * @throws IOException
     */
    public void sendMessage(String message) {
        try {
            objectOutputStream.writeObject(new Message(message));
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }
    }


    /**
     * Sends the username to the OutPutStream
     *
     * @throws IOException
     */
    public void sendUserName() {
        try {
            objectOutputStream.writeObject(getUsername());
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeConnection();
        }

    }


    /**
     * Reads the ChatLog from the server and updates the chat log stored on the client.
     *
     * @return true or false
     * @throws Exception
     */
    public boolean readMessage() {
        try {
            List tmp = new ArrayList<>();
            tmp = (ArrayList) objectInputStream.readObject();

            for (Object o : tmp) {
                System.out.println(o.getClass().getName());
                if (o.getClass() != Message.class) {
                    onlineUsers = (ArrayList<String>) tmp;
                    System.out.println(onlineUsers);
                    return true;
                }
            }

            chatLog = (ArrayList<Message>) tmp;
            System.out.println(chatLog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            closeConnection();
        }
        return false;
    }


    /**
     * Closes the client connection in case of an error.
     * Closes all sockets and streams to free resources.
     *
     * @throws IOException
     */
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
            e.printStackTrace();
        }
    }


}

















