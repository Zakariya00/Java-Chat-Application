import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Receives the messages from each client and stores all the messages in an arraylist and sends them to the clients.

public class ServerModel implements Runnable {

    //stores all chat history
    public static ArrayList<String> messages = new ArrayList<String>();

    private ArrayList<String> connectionLog = new ArrayList<String>();

    //allows the server to send messages to multiple clients. Each connection handler handles one client.
    private ArrayList<ConnectionHandler> connections = new ArrayList<>();

    //accepts client connection
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
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            //do nothing
        }

    }




}
