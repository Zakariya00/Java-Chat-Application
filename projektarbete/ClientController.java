import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// The ClientGUI displays the information that the client gets from the server in the chat window.
// It also works as a Controller, where it lets the client send messages to the server when it presses the send message button.
//It works both as controller and as a ClientView. This is because the ClientController.form works as the ClientView.

public class ClientController extends JFrame {

    //the form handles and creates the ClientView automatically.
    private JPanel mainPanel;
    private JPanel ConnectPanel;
    private JPanel ChatPanel;
    private JFormattedTextField formattedTextField;
    private JButton connectButton;
    private JTextField messageField;
    private JTextArea textArea;
    private JButton sendMessageButton;

    private CardLayout cl = (CardLayout) mainPanel.getLayout(); //denna layout tillåter att man går från connect window till chat window på ett smidigt sätt

    private ClientModel clientModel;


    public ClientController(ClientModel client) {

        super("client");
        this.clientModel = client;
        textArea.setEditable(false);
        add(mainPanel);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(mainPanel,"Card2");

                //write to the server when the connect button is pressed
                client.setUsername(formattedTextField.getText());
                client.sendMessage("User " + formattedTextField.getText() + " has connected to the server");

                //read the response from the server and display it
                displayMessage();

            }
        });


        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)

            {

                client.sendMessage(client.getUsername() + " disconnected from the server");

            }


        });



        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //write from the client to the server when the send button is pressed
                client.sendMessage(client.getUsername() + ": " + messageField.getText());
                messageField.setText("");

                //read the response to the client from the server and display it
                displayMessage();


            }
        });


    }


    public void displayMessage(){

        ArrayList<String> chatLog = clientModel.readMessage(); //reads the message that server has sent to clients inputstream and displays it in the window.
        textArea.setText("");
        for (String msg : chatLog){
            textArea.append(msg + "\n");
        }

    }






    public static void main (String[] args){



        ClientModel client = new ClientModel();
        client.run();

        ClientController GUI = new ClientController(client);







    }


}

























