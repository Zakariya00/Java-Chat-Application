package server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


//the server will have a GUI.

//nånting fel med servern. GUI visas inte av någon anledning.


public class ServerController extends JFrame {


    private ServerModel serverModel;
    private ServerView serverView;

    public ServerController(ServerModel serverModel){

        super("Server");
        this.serverModel = serverModel;
        this.serverView = new ServerView(serverModel);

        //serverView.textArea.append("Server up and running on port "+serverModel.getPort()+"\n");
        //serverView.textArea.append("Local Server IP address is "+serverModel.getIpAddress());
        serverView.displayMessages(); //--------------------------------------------------------------------------------

        add(serverView.serverPanel);

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);


        // Server shutdown button opens JOptionPane --------------------------------------------------------------------
        serverView.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //implement server function to no notiffy clients of shutdown and disconnect before shutdown


                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to shutdown the Server?");
                if (input == JOptionPane.YES_NO_OPTION) {
                    System.out.println("Server Shutdown");
                    serverModel.serverShutDown();

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }

                //Test code for view update methods
                //serverView.displayUsers();
                //serverView.displayMessages();
            }
        });

        //JOptionPane if window is closed ------------------------------------------------------------------------------
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

               int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to shutdown the Server?");
               if(input == JOptionPane.YES_NO_OPTION) {
                   serverModel.serverShutDown();
                   System.out.println("Server Shutdown");

                   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                   System.exit(0);
               }
            }
        });

    }


    //Automatically update Server Gui for new messages and Users -------------------------------------------------------
    public void serverlistenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    //ListIterator itr1 = serverModel.getOnlineUsers().listIterator();
                    //ListIterator itr2 = serverModel.getChatLog().listIterator();
                    //if (itr1.hasNext() || itr1.hasPrevious()) { serverView.displayUsers();}
                    //if (itr2.hasNext()) { serverView.displayMessages();}

                   if (serverModel.getOnlineUsersCopy().size() != serverModel.getOnlineUsers().size()) {
                       serverModel.updateOnlineUsersCopy();
                       serverView.displayUsers();
                   }

                    if (serverModel.getChatLogCopy().size() != serverModel.getChatLog().size()) {
                        serverModel.updateChatLogCopy ();
                        serverView.displayMessages();
                    }
                }
            }
        }).start();
    }



    public static void main(String[] args) {


        ServerModel serverModel = new ServerModel();

        ServerController serverController = new ServerController(serverModel);
        serverController.serverlistenForMessage(); // ------------------------------------------------------------------
        //den fastnar i servermodel while loop. du måste ge den efteråt
        serverModel.startServer();




    }




}
