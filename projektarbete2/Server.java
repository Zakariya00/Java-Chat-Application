import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    //stores all chat history
    private static ArrayList<String> messages = new ArrayList<String>();

    private ServerSocket serverSocket;
    private ExecutorService pool;

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(1234);
            pool = Executors.newCachedThreadPool();
            //always listen for new client connections
            while (true) {
                Socket client = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
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

        public ConnectionHandler(Socket client){
            this.client = client;
        }

        @Override
        public void run() {
            try {

                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectInputStream = new ObjectInputStream(client.getInputStream());

                while (true){


                    //read message from client and stores it in the arraylist.
                    Packet msgFromClient = (Packet) objectInputStream.readObject();
                    System.out.println("Server received from client: " +msgFromClient.message);

                    //write to all the clients GUI. How can this be accomplished?
                    messages.add(msgFromClient.message);
                    System.out.println(messages);

                    ArrayList<String> messagesCopy = new ArrayList<String>(messages);
                    //sends the arraylist to the client.

                    objectOutputStream.writeObject(messagesCopy);
                    objectOutputStream.flush(); //why do we flush the object outputstream?

                }



            }catch (IOException | ClassNotFoundException e){

            }
        }

    }



}
