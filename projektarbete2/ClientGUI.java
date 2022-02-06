import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

//the ClientGUI displays the information that the client gets from the server. It also works as a Controller, where it lets the client send messages to the server.

public class ClientGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel ConnectPanel;
    private JPanel ChatPanel;
    private JFormattedTextField formattedTextField;
    private JButton connectButton;
    private JTextField messageField;
    private JTextArea textArea;
    private JButton sendMessageButton;
    private CardLayout cl = (CardLayout) mainPanel.getLayout(); //denna layout tillåter att man går från connect window till chat window på ett smidigt sätt


    private Client client;


    public ClientGUI(Client client) {

        super("client");
        this.client = client;

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


        //1. we need to find a way to make the clients read the message from the objectoutputstream without pressing the send button
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)

            {

                client.sendMessage(client.getUsername() + " disconnected from the server");

            }


        });



        //2.this only runs when we press the send button.

        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //write to the server when the send button is pressed
                client.sendMessage(client.getUsername() + ": " + messageField.getText());
                messageField.setText("");

                //read the response from the server and display it
                displayMessage();


            }
        });


    }


    public void displayMessage(){

        ArrayList<String> chatLog = client.readMessage(); //reads the message that server has sent to clients inputstream.
        textArea.setText("");
        for (String msg : chatLog){
            textArea.append(msg+"\n");
        }

    }




    public static void main (String[] args){
        Client client = new Client();
        client.run();
        new ClientGUI(client);

    }






}

























