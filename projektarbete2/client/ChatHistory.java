package client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import message.Packet;

public class ChatHistory {
    private File file = null;
    private ClientModel clientmodel ;

    public ChatHistory(){
        clientmodel = new ClientModel();
    }




    /*public static void main (String[] args) {

        File file = null;
        List<Packet> history = new ArrayList<Packet>();

        history.add(new Packet("love"));
        history.add(new Packet(" "));
        history.add(new Packet("whigh"));

    }*/



    public void SaveChatLog() throws IOException {

        try {
            file = new File( "chatHistory.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream("chatHistory.txt");
            DataOutputStream Out = new DataOutputStream(fos);
            System.out.println(clientmodel.getChatLog());
            for (Packet chat : clientmodel.getChatLog()) {
                Out.write((chat.toString().getBytes()));

            }
            Out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
