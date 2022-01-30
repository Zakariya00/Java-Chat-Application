//1 thread receives all messages from server
//1 thread for receiving console line input

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    @Override
    public void run() {
        try {
            client = new Socket("localhost", 1234);
            out = new PrintWriter(client.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine())!=null){
                System.out.println(inMessage);
            }


        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown(){
        done=true;
        try{
            in.close();
            out.close();
            if (!client.isClosed()){
                client.close();
            }
        } catch(IOException e){
            //ignore
        }
    }

    class InputHandler implements Runnable {


        @Override
        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done){
                    String message = inReader.readLine();
                    out.println(message);

                }
            } catch (IOException e){
                shutdown();
            }
        }
    }




    public static void main(String[] args){
        Client client = new Client();
        client.run();
    }







}

