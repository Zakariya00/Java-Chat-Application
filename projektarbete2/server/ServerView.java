package server;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import client.ClientModel;
import message.Message;

/**
 * The ServerView displays the information that the server gets from the client.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 * @version 1.0 3/2/2022
 */
public class ServerView {
    private static List<Message> displayedMessages = new ArrayList<Message>();
    private static List<String> displayedOnlineUsers = new ArrayList<>();
    public JButton loadButton;
    public ServerModel serverModel;
    private JPanel serverPanel;
    private JTextArea textArea;
    private JButton button;
    private JTextArea textArea2;
    private JButton saveButton;
    private ClientModel clientModel;

    /**
     * class constructor for initializing servermodel and teh view.
     *
     * @param serverModel initialising servermodel
     */
    public ServerView(ServerModel serverModel) {

        this.serverModel = serverModel;
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea2.setEditable(false);
        textArea2.setLayout(new GridLayout(0, 1));

    }

    /**
     * displays client messages on the server GUI
     */
    public void displayMessages() {
        textArea.setText("");
        textArea.append("Server up and running on port " + serverModel.getPort() + "\n");
        textArea.append("Local Server IP address is " + serverModel.getIpAddress() + "\n\n");

        ArrayList<Message> chatLog = ServerModel.getChatLog();
        for (Message msg : chatLog) {
            textArea.append(msg.toString() + "\n");
        }
    }

    /**
     * Displays list of online users
     */
    public void displayUsers() {
        ArrayList<String> users = ServerModel.getOnlineUsers();
        textArea2.setText("");

        for (String user : users) {
            textArea2.append(" " + user + "\n");
        }
    }

    /**
     * access the serverpanel
     *
     * @return serverpanel
     */
    public JPanel getServerPanel() {
        return this.serverPanel;
    }

    /**
     * accesses the button to add listerners
     *
     * @return button
     */
    public JButton getButton() {
        return this.button;
    }

    /**
     * access save button to add listener
     *
     * @return button
     */
    public JButton getsaveButton() {
        return saveButton;
    }

    /**
     * access button to add listener
     *
     * @return loadbutton
     */
    public JButton getloadButton() {
        return loadButton;
    }

    /**
     * gets the messages to be displayed
     *
     * @return message array
     */
    public ArrayList<Message> getDisplayedMessages() {
        return new ArrayList<>(displayedMessages);
    }

    /**
     * Gets the list of online users
     *
     * @return online users
     */
    public ArrayList<String> getDisplayedOnlineUsers() {
        return new ArrayList<>(displayedOnlineUsers);
    }

    /**
     * updates the displaymessage array
     */
    public void updateDisplayedMessages() {
        displayedMessages = ServerModel.getChatLog();
    }

    /**
     * updates displayed online user list.
     */
    public void updateDisplayedOnlineUsers() {
        displayedOnlineUsers = ServerModel.getOnlineUsers();
    }

}
