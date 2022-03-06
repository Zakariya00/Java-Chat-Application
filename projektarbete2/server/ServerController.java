package server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * ServerConroller class for handlng listeners to the serverview
 *
 * @version 1.0 3/2/2022
 * @Author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 */
public class ServerController extends JFrame {

    private final ServerModel serverModel;
    private final ServerView serverView;

    /**
     * class constructor that adds listeners to the serverview
     *
     * @param serverModel initialisng the servermodel
     */
    public ServerController(ServerModel serverModel) {

        super("Server");
        this.serverModel = serverModel;
        this.serverView = new ServerView(serverModel);

        serverView.displayMessages();
        add(serverView.getServerPanel());

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        serverView.getsaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                serverModel.save();
            }
        });

        serverView.getloadButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                serverModel.load();
                serverModel.sendMessages();
            }
        });

        serverView.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to shutdown the Server?");
                if (input == JOptionPane.YES_NO_OPTION) {
                    serverModel.serverShutDown();
                    System.exit(0);
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to shutdown the Server?");
                if (input == JOptionPane.YES_NO_OPTION) {
                    serverModel.serverShutDown();
                    System.exit(0);
                }
            }
        });

    }

    public static void main(String[] args) {

        ServerModel serverModel = new ServerModel();
        ServerController serverController = new ServerController(serverModel);
        serverController.serverlistenForMessage();
        serverModel.startServer();
    }

    /**
     * Automatically update Server Gui for new messages and Users
     */
    public void serverlistenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    if (serverView.getDisplayedOnlineUsers().size() != ServerModel.getOnlineUsers().size()) {
                        serverView.updateDisplayedOnlineUsers();
                        serverView.displayUsers();

                    }

                    if (serverView.getDisplayedMessages().size() != ServerModel.getChatLog().size()) {
                        serverView.updateDisplayedMessages();
                        serverView.displayMessages();
                    }
                }
            }
        }).start();
    }

}
