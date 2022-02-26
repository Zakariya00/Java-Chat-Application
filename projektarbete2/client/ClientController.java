package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//handles button presses and things that affect the model.
//it should not display messages in reality, that should be handled by the view.

public class ClientController extends JFrame {

    private ClientModel clientModel;
    private ClientView clientView;
    private CardLayout cl; //denna layout tillåter att man går från connect window till chat window på ett smidigt sätt




    public ClientController(ClientModel clientModel) {
        super("client");
        this.clientModel = clientModel;
        this.clientView = new ClientView(clientModel);
        add(clientView.mainPanel);

        cl = (CardLayout) clientView.mainPanel.getLayout();


        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        clientView.connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //write to the server when the connect button is pressed
                if (!clientView.formattedTextField.getText().equals("")) {
                    cl.show(clientView.mainPanel, "Card2");
                    clientModel.setUsername(clientView.formattedTextField.getText());
                    clientModel.sendUserName(); // Connect button sends username to Server ---------------------------------
                    clientModel.sendMessage("User " + clientView.formattedTextField.getText() + " has connected to the server");
                    setTitle("Client - " + clientModel.getUsername());
                } else {
                    //play a sound here - r
                    clientView.connectionError.setText("Please enter a valid username.");
                }



            }
        });

        clientView.sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //write from the client to the server when the send button is pressed
                clientModel.sendMessage(clientModel.getUsername() + ": " + clientView.messageField.getText());
                clientView.messageField.setText("");



            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                clientModel.sendMessage("User "+clientModel.getUsername() + " disconnected from the server");

            }


        });




    }


    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    clientModel.readMessage(); //read the inputstream
                    clientView.displayMessage(); //display to the view
                }
            }
        }).start();

    }




    public static void main (String[] args){



        ClientModel clientModel = new ClientModel();
        ClientController clientController = new ClientController(clientModel);
        clientController.listenForMessage();

    }

}
