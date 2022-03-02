package server;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChatHistory {
    private File file = null;
    private ServerModel serverModel ;

    private String fileName;

    public ChatHistory(ServerModel servermodel){
        serverModel= servermodel;
        fileName = "chatHistory.txt";


    }
    /*
    public static void main (String[] args) {
        ClientModel model = new ClientModel();
        ChatHistory hh = new ChatHistory(model);
        //hh.ReadChatLog();

    }

     */

    public void SaveChatLog() throws IOException {
        try {
            fileName = "chatHistory.txt";
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //FileOutputStream fos = new FileOutputStream("chatHistory.txt");
            FileOutputStream fos = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(fos);
            for (String chat : serverModel.getChatLog())
                pw.println(chat);
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadChatLog(){
        try{

            FileInputStream fis = new FileInputStream("chatHistory.txt");
            byte [] data =new byte[1000];
            ArrayList<String> History = new ArrayList<>();
            fis.read(data);
            fis.close();
            String s = new String(data, StandardCharsets.UTF_8);
            History.add(s);
            serverModel.messages.add(s);
            ClientHandler. broadcastMessage(History);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogFileName() {
        return fileName;
    }

    public boolean HistoryExists() {
        File tmp = new File(getLogFileName());
        boolean HistoryExists = tmp.isFile() && tmp.canRead();
        return HistoryExists;
    }


}
