package server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


//the server will have a GUI.

//nånting fel med servern. GUI visas inte av någon anledning.


public class ServerController extends JFrame {


    private ServerModel serverModel;
    private ServerView serverView;
    //private ChatHistory chathistory;

    public ServerController(ServerModel serverModel){

        super("Server");
        this.serverModel = serverModel;
        this.serverView = new ServerView(serverModel);
        //this.chathistory = new ChatHistory(serverModel);

        serverView.displayMessages(); //------------------------------------------------
        add(serverView.getServerPanel());

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);

        // Save Button  -----------
        serverView.getsaveButton().addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
                serverModel.save();
            }
        });

        // Load Button
        serverView.getloadButton().addActionListener(new ActionListener()  {
        public void actionPerformed(ActionEvent e) {
            serverModel.load();
            serverModel.sendMessages();
        }
    });



        // Server shutdown button opens JOptionPane ----------------------------------------
        serverView.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //implement server function to no notiffy clients of shutdown and disconnect before shutdown


                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to shutdown the Server?");
                if (input == JOptionPane.YES_NO_OPTION) {
                    System.out.println("Server Shutdown");
                    serverModel.serverShutDown();
                    System.exit(0);
                }
            }
        });

        //JOptionPane if window is closed ----------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

               int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to shutdown the Server?");
               if(input == JOptionPane.YES_NO_OPTION) {
                   serverModel.serverShutDown();
                   System.out.println("Server Shutdown");
                   System.exit(0);
               }
            }
        });

    }


    //Automatically update Server Gui for new messages and Users -----------------------------
    public void serverlistenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                   if (serverView.getDisplayedOnlineUsers().size() != serverModel.getOnlineUsers().size()) {
                       serverView.updateDisplayedOnlineUsers();
                       serverView.displayUsers();


                   }

                    if (serverView.getDisplayedMessages().size() != serverModel.getChatLog().size()) {
                        serverView.updateDisplayedMessages ();
                        serverView.displayMessages();
                    }
                }
            }
        }).start();
    }



    public static void main(String[] args) {

        ServerModel serverModel = new ServerModel();
        ServerController serverController = new ServerController(serverModel);
        serverController.serverlistenForMessage(); // --------------------------------------------
        //den fastnar i servermodel while loop. du måste ge den efteråt
        serverModel.startServer();
    }


}
