package server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import message.Packet;
import user.ClientUserName;

public class ServerView {
    private JPanel serverPanel;
    private JTextArea textArea; // Chat area
    private JButton button; // Server Shutdown button
    private JTextArea textArea2; // Online users area
    private ServerModel serverModel;

    private static List<Packet> displayedMessages = new ArrayList<Packet>(); // -----------------------
    private static List<ClientUserName> displayedOnlineUsers = new ArrayList<ClientUserName>(); // ------------------------



    public ServerView(ServerModel serverModel){
        this.serverModel = serverModel;
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea2.setEditable(false);
        textArea2.setLayout(new GridLayout(0,1));
        //textArea2.setEditable(false);

    }


    //Displays Client messages on Server GUI ----------------------------------------------------
    public void displayMessages() {
        ArrayList<Packet> msgs = new ArrayList<Packet>();
        msgs.add(new Packet("Server up and running on port "+serverModel.getPort()));
        msgs.add(new Packet("Local Server IP address is "+serverModel.getIpAddress()+"\n"));
        msgs.addAll(serverModel.getChatLog());

        textArea.setText("");
        for (Packet msg : msgs) {
            textArea.append(msg.toString() + "\n");
        }
    }


    //Displays Connected Clients usernames on Server Gui ---------------------------------------
    public void displayUsers() {
        ArrayList<ClientUserName> users = serverModel.getOnlineUsers();
        textArea2.setText("");

        for (ClientUserName user : users) {
          textArea2.append(" "+user.toString() + "\n");
        }
    }


    //Getter methods for private Instance Variables
    public JPanel getServerPanel() {return this.serverPanel;}
    public JButton getButton() {return this.button;}

    public ArrayList<Packet> getDisplayedMessages() {return new ArrayList<>(displayedMessages);}
    public ArrayList<ClientUserName> getDisplayedOnlineUsers() {return new ArrayList<>(displayedOnlineUsers);}

    // Updates Displayed Messages and OnlineUsers ---------------------------------------------------
    public void updateDisplayedMessages () { displayedMessages = serverModel.getChatLog();}
    public void updateDisplayedOnlineUsers () {displayedOnlineUsers = serverModel.getOnlineUsers();}


}
