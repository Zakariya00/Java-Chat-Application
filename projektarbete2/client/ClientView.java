package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import client.*;
import user.ClientUserName;
import message.Packet;

// The ClientGUI displays the information that the client gets from the server in the chat window.
// It also works as a Controller, where it lets the client send messages to the server when it presses the send message button.
//It works both as controller and as a ClientView. This is because the ClientController.form works as the ClientView.

public class ClientView {

    //the form handles and creates the ClientView automatically.
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

    //takes a clientModel(which stores all messages for a client) and displays it
    private ClientModel clientModel;

    public ClientView(){
        textArea.setEditable(false);
        textArea.setLineWrap(true);
    }

    public void setClientModel(ClientModel clientModel){
        this.clientModel = clientModel;
    }


    public void displayMessage(){
        ArrayList<Packet> chatLog = clientModel.getChatLog(); //reads the message that server has sent to clients inputstream and displays it in the window.
        textArea.setText("");
        for (Packet msg : chatLog){
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

}
