package client;

import java.io.*;

public class ChatHistory {
    private File file = null;
    private ClientModel clientmodel ;

    public ChatHistory(){
        clientmodel = new ClientModel();
    }

/*
    public static void main (String[] args) {

       /* File file = null;
        List<String> history = new ArrayList<String>();

        history.add("love");
        history.add(" ");
        history.add("high");

    }
*/

    public void SaveChatLog() throws IOException {

        try {
            file = new File("chatHistory.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream("chatHistory.txt");
            DataOutputStream Out = new DataOutputStream(fos);
            System.out.println(clientmodel.getChatLog());
            for (String chat : clientmodel.getChatLog()) {
                Out.write((chat).getBytes());

            }
            Out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
