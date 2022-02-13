import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// The ClientGUI displays the information that the client gets from the server in the chat window.
// It also works as a Controller, where it lets the client send messages to the server when it presses the send message button.
//It works both as controller and as a ClientView. This is because the ClientController.form works as the ClientView.

public class ClientView {

    //the form handles and creates the ClientView automatically.
    public JPanel mainPanel;
    private JPanel ConnectPanel;
    private JPanel ChatPanel;
    public JFormattedTextField formattedTextField;
    public JButton connectButton;
    public JTextField messageField;
    public JTextArea textArea;
    public JButton sendMessageButton;

    //takes a clientModel(which stores all messages for a client) and displays it
    private ClientModel clientModel;

    public ClientView(ClientModel clientModel){
        textArea.setEditable(false);
        this.clientModel = clientModel;
    }


}

























