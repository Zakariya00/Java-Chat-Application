package client;

import javax.swing.*;
import java.util.ArrayList;
import message.Message;
import user.ClientUserName;

/**
 * The ClientView displays the information that the client gets from the server in the chat window.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 *
 * @version 1.0 3/2/2022
 */

public class ClientView {


    private JPanel mainPanel;
    private JPanel ConnectPanel;
    private JPanel ChatPanel;
    private JFormattedTextField formattedTextField;
    private JButton connectButton;
    private JTextField messageField;
    private JTextArea textArea;
    private JButton sendMessageButton;
    private JLabel connectionError;
    private JTextArea textArea1;
    private ClientModel clientModel;


    /**
     * Provides the ClientView with a clientModel so that it can display data and fetch data from the clientModel.
     * Makes the text area which displays all the messages uneditable.
     * Sets lineWrap to true so that long messages will break on the chat window instead of creating a horizontal scroll policy.
     */

    public ClientView(){
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea1.setEditable(false);
    }


    /**
     * Displays all the messages in the client chatlog in the client chat window.
     */
    public void displayMessage(){
        ArrayList<Message> chatLog = clientModel.getChatLog(); //reads the message that server has sent to clients inputstream and displays it in the window.
        textArea.setText("");
        for (Message msg : chatLog){
            textArea.append(msg.toString() + "\n");
        }
    }

    public void displayUsers() {
        ArrayList<ClientUserName> users = clientModel.getOnlineUsers(); //reads the message that server has sent to clients inputstream and displays it in the window.
        textArea1.setText("");
        for (ClientUserName user : users){
            textArea1.append(user.toString() + "\n");
        }
    }

    // Getter methods for Private Instance Variables
    public JPanel getmainPanel(){return this.mainPanel;}
    public JPanel getConnectPanel() {return this.ConnectPanel;}
    public JPanel getChatPanel() {return this.ChatPanel;}
    public JFormattedTextField getformattedTextField() {return this.formattedTextField;}
    public JButton getconnectButton() {return this.connectButton;}
    public JTextField getmessageField() {return this.messageField;}
    public JButton getsendMessageButton() {return this.sendMessageButton;}
    public JLabel getconnectionError() {return this.connectionError;}

    public void setClientModel(ClientModel clientmodel) {this.clientModel = clientmodel;}

}
