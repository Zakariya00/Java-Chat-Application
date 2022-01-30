import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;


        //2 while loops
        //first while loop makes sure that the server is always running
        //second while loop makes sure that client is always connected to the server
        while (true){

            try{

                //the accept method of the serverSocket waits for a client connection. Once connected, a socket object is returned which can be used to communicate with the client.
                socket = serverSocket.accept();

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                //used to read messages from the client
                bufferedReader = new BufferedReader(inputStreamReader);
                //used to write messages to the client
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                while (true){

                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("client: " + msgFromClient);

                    bufferedWriter.write("MSG RECEIVED");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    if (msgFromClient.equalsIgnoreCase("BYE")){
                        break;
                    }

                }

                //if client sent "Bye", we want to close all resources the client was using

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();



            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

}
