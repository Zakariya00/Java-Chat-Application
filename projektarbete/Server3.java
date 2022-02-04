import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server3 {

    //stores all chat history
    private static ArrayList<String> messages = new ArrayList<String>();


    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;


        //2 while loops
        //first while loop makes sure that the server is always running
        //second while loop makes sure that client is always connected to the server
        while (true){

            try{

                //the accept method of the serverSocket waits for a client connection. Once connected, a socket object is returned which can be used to communicate with the client.
                socket = serverSocket.accept();

                //used to write messages to the client(packet objects).
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                //used to read messages from the client(packet objects).
                objectInputStream = new ObjectInputStream(socket.getInputStream());


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



            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally{




                serverSocket.close();
                socket.close();
                objectOutputStream.close();
                objectInputStream.close();





            }


        }



    }



}
