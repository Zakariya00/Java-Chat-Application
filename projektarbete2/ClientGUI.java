import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
                client.setUsername(formattedTextField.getText());
                client.sendMessage("User " + formattedTextField.getText() + " has connected to the server");
                cl.show(mainPanel,"Card2");


                //read the response from the server and display it
                ArrayList<String> chatLog = client.readMessage();
                textArea.setText("");
                for (String msg : chatLog){
                    textArea.append(msg+"\n");
                }


            }
        });


        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage(client.getUsername() + ": " + messageField.getText());
                messageField.setText("");   //ta bort texten från message field som client skrivit. Detta så att client kan skriva ett nytt message.


                //read the response from the server and display it
                ArrayList<String> chatLog = client.readMessage();
                textArea.setText("");
                for (String msg : chatLog){
                    textArea.append(msg+"\n");
                }

            }
        });
    }




    public static void main (String[] args){
        Client client = new Client();
        client.run();
        new ClientGUI(client);

    }

}
