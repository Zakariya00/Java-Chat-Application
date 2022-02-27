package server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ServerView {
    public JPanel serverPanel;
    private JTextArea textArea; // Chat area
    public JButton button; // Server Shutdown button
    private JTextArea textArea2; // Online users area
    private JLabel connectionLog;

    private ServerModel serverModel;


    public ServerView(ServerModel serverModel){
        this.serverModel = serverModel;
        textArea.setEditable(false);
        textArea2.setEditable(false);
        textArea2.setLayout(new GridLayout(0,1));
        //textArea2.setEditable(false);


    }


    //Displays Client messages on Server GUI ---------------------------------------------------------------------------
    public void displayMessages() {
        ArrayList<String> msgs = new ArrayList<String>();
        msgs.add("Server up and running on port "+serverModel.getPort());
        msgs.add("Local Server IP address is "+serverModel.getIpAddress()+"\n");
        msgs.addAll(serverModel.getChatLog());

        textArea.setText("");
        for (String msg : msgs) {
            textArea.append(msg + "\n");
        }
    }


    //Displays Connected Clients usernames on Server Gui ---------------------------------------------------------------
    public void displayUsers() {
        ArrayList<String> users = serverModel.getOnlineUsers();
        textArea2.setText("");

        for (String user : users) {
          textArea2.append(" "+user + "\n");
        }
    }



}
