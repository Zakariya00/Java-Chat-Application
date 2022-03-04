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


    public ClientController() {
        super("client");

        this.clientModel = new ClientModel();
        this.clientView = new ClientView();

        add(clientView.getmainPanel());

        cl = (CardLayout) clientView.getmainPanel().getLayout();


        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);


        clientView.getconnectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //retry the connection for the clientModel
                if (clientModel.getSocket() == null){
                    clientModel.connectToServer();
                }

                //pass the updated clientModel to the clientView
                clientView.setClientModel(clientModel);



                if (clientModel.getSocket()==null){
                    clientView.getconnectionError().setText("Server is not running.");
                } else if (clientView.getformattedTextField().getText().equals("")) {
                    //play a sound here - r
                    clientView.getconnectionError().setText("Please enter a valid username.");
                } else if (clientView.getformattedTextField().getText().length()>15){
                    clientView.getconnectionError().setText("Username can't be longer than 15 characters");
                } else {

                    cl.show(clientView.getmainPanel(), "Card2");
                    clientModel.setUsername(clientView.getformattedTextField().getText());
                    clientModel.sendUserName(); // Connect button sends username to Server ---------------------------------
                    clientModel.sendMessage("User " + clientView.getformattedTextField().getText() + " has connected to the server");
                    setTitle("Client - " + clientModel.getUsername());

                }
            }
        });

        clientView.getsendMessageButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //write from the client to the server when the send button is pressed
                clientModel.sendMessage(clientModel.getUsername() + ": " + clientView.getmessageField().getText());
                clientView.getmessageField().setText("");

            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                int input = JOptionPane.showConfirmDialog(null,"Are you sure you want to close the client?");
                if(input == JOptionPane.YES_NO_OPTION) {

                    //this allows you to close the window if server is not running. it wont sendMessage.
                    if (clientModel.getSocket()!=null) {
                        clientModel.sendMessage("User " + clientModel.getUsername() + " disconnected from the server");
                    }

                    System.out.println("client shutdown");
                    System.exit(0);
                }
            }


        });




    }


    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    if(clientModel.readMessage()) {
                        clientView.displayMessage();
                        clientView.displayUsers();
                    }
                }
            }
        }).start();
    }




    public static void main (String[] args){
        ClientController clientController = new ClientController();
        clientController.listenForMessage();

    }

}
