import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client3 {

    public static String username = "";  //is there another way to solve this, without using public static?

    public static void main(String[] args) {

        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;


        try {

            socket = new Socket("localhost",1234);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); //skapa en stream där man kan skicka objekt. detta behövs eftersom vi skickar arraylist som är ett objekt. en vanlig outputstream hade inte funkat.
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            ClientGUI2 GUI = new ClientGUI2(); //skapa GUI


            //while loop for sending messages
            while (true) {


                String message="";

                //while the user has not pressed send button, wait for them to press send.
                while (!GUI.sendButtonPressed()) {
                    message = GUI.getMessage();
                }

                Packet msgToServer = new Packet(username+": "+message);
                GUI.unPressButton(); //unPressTheButton so that you can send another message.

                System.out.println("Client sent to server: "+msgToServer.message);

                //send the message you wrote to the server
                objectOutputStream.writeObject(msgToServer);
                objectOutputStream.flush();


                //this reads message from server
                ArrayList<String> chatLog = new ArrayList<String>();
                chatLog = (ArrayList<String>) objectInputStream.readObject();
                System.out.println(chatLog);

                GUI.clearTextArea();
                for (String msg : chatLog){
                    GUI.writeToTextArea(msg);
                }



            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                if (socket != null){
                    socket.close();
                }
                if (objectInputStream != null){
                    objectInputStream.close();
                }
                if (objectOutputStream != null){
                    objectOutputStream.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }


    }

}















