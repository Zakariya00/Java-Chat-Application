import java.io.*;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    //allows us to send messages to multiple clients
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader; //read data, messages that have been sent from the client
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket){

        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");

        } catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }


    }




    @Override
    public void run() {
        //everything in the run method is run on a separate thread.


        String messageFromClient;

        while (socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine(); //this is a blocking process. That's why it needs its own thread if we want to handle multiple clients.
                broadcastMessage(messageFromClient);


            } catch (IOException e){
                closeEverything(socket,bufferedReader,bufferedWriter);
                break; //to exit the while loop
            }
        }







    }


    public void broadcastMessage(String messageToSend){

        for (ClientHandler clientHandler : clientHandlers){
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)){

                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();

                }
            } catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }

    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER " + clientUsername + " has left the chat");
    }


    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWrter){
        removeClientHandler();

        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWrter.close();
            }
            if (socket != null){
                socket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }
}


































