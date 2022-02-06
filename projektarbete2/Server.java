import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Receives the messages from each client and stores all the messages in an arraylist and sends them to the clients.

public class Server implements Runnable {

    //stores all chat history
    private static ArrayList<String> messages = new ArrayList<String>();

    private ServerSocket serverSocket;
    private ExecutorService pool;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1234);
            pool = Executors.newCachedThreadPool();
            //always listen for new client connections
            while (true) {
                Socket client = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(client); //handles the connection between the server and that specific client
                pool.execute(handler);
            }
        } catch (IOException e) {
            //do nothing
        }

    }

    class ConnectionHandler implements Runnable { //can you only have 1 public java class per class file?

        private Socket client;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;

        //allows the server to send messages to multiple clients.
        private static ArrayList<ConnectionHandler> connectionHandlers = new ArrayList<>();

        public ConnectionHandler(Socket client){
            this.client = client;
            connectionHandlers.add(this);
        }

        public void closeConnectionHandler(){
            try {
                if (client != null) {
                    client.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            try {

                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectInputStream = new ObjectInputStream(client.getInputStream());

                while (true){


                    //read message from 1 client and stores it in the arraylist.
                    Packet msgFromClient = (Packet) objectInputStream.readObject();
                    System.out.println("Server received from client: " +msgFromClient.message);

                    //write to all the clients GUI. How can this be accomplished?
                    messages.add(msgFromClient.message);
                    System.out.println(messages);

                    ArrayList<String> messagesCopy = new ArrayList<String>(messages);


                    //sends the arraylist to all the clients. We also want all clients objectInputStream to read it
                    objectOutputStream.writeObject(messagesCopy);
                    objectOutputStream.flush(); //why do we flush the object outputstream?




                }



            }catch (IOException | ClassNotFoundException e){
                //do nothing
            } finally {

                //connectionHandlers.remove(this);
                //closeConnectionHandler();

            }
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }




}
