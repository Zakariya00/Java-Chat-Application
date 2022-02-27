package server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ServerView {
    private JPanel serverPanel;
    private JTextArea textArea; // Chat area
    private JButton button; // Server Shutdown button
    private JTextArea textArea2; // Online users area
    private JLabel connectionLog;
    private ServerModel serverModel;

    private static List<String> displayedMessages = new ArrayList<String>(); // --------------------------------
    private static List<String> displayedOnlineUsers = new ArrayList<String>(); // ------------------------



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


    //Getter methods for private Instance Variables
    public JPanel getServerPanel() {return this.serverPanel;}
    public JButton getButton() {return this.button;}

    public ArrayList<String> getDisplayedMessages() {return new ArrayList<>(displayedMessages);}
    public ArrayList<String> getDisplayedOnlineUsers() {return new ArrayList<>(displayedOnlineUsers);}

    // Updates Displayed Messages and OnlineUsers -------------------------------------------------------------------------
    public void updateDisplayedMessages () { displayedMessages = serverModel.getChatLog();}
    public void updateDisplayedOnlineUsers () {displayedOnlineUsers = serverModel.getOnlineUsers();}


}
