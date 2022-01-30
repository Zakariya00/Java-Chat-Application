import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//make the server GUI and make it store all messages

public class Server implements Runnable{


    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService threadPool;


    public Server() {
        connections = new ArrayList<>();
        done=false;
    }


    @Override
    public void run() {
        //the code that executes when we start the server class
        try {
            server = new ServerSocket(1234);
            threadPool = Executors.newCachedThreadPool();
            while(!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                threadPool.execute(handler);
            }

        } catch (Exception e) {
            shutdown();
        }
    }




    public void broadcast(String message){
        for (ConnectionHandler ch: connections){
            if (ch != null){
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown() {
        try {
            done=true;
            threadPool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections){
                ch.shutdown();
            }
        } catch(IOException e){
            //ignore
        }
    }


    class ConnectionHandler implements Runnable {


        private Socket client;
        private String nickname;

        //in is used when we want to read a message client has sent
        private BufferedReader in;
        //out is used when we want to write message client has sent
        private PrintWriter out;

        //this will take the client connection from the server
        public ConnectionHandler(Socket client){
            this.client = client;
        }

        @Override
        public void run() {

            try{
                out = new PrintWriter(client.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter a nickname: ");
                nickname = in.readLine();
                System.out.println(nickname + " connected!");
                //we also want to broadcast this message to all the other clients
                broadcast(nickname+" has joined the chat");

                String message;
                while ((message = in.readLine()) != null){
                    broadcast(nickname + ": " + message);
                }


            } catch (IOException e) {
                broadcast(nickname+" has left the server");
                shutdown();
            }

        }


        public void sendMessage(String message){
            out.println(message);
        }

        public void shutdown() {
            try {
                in.close();
                out.close();

                if (!client.isClosed()) {
                    client.close();
                }
            }
        catch (IOException e) {
            //ignore
        }
        }



    }


    public static void main (String[] args){
        Server server = new Server();
        server.run();
    }


}

