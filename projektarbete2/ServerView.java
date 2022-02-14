package server;

import javax.swing.*;

public class ServerView {
    public JPanel serverPanel;
    public JTextArea textArea;
    private JButton button;
    private JTextArea textArea2;
    private JLabel connectionLog;
    private ServerModel serverModel;




    public ServerView(ServerModel serverModel){

        this.serverModel = serverModel;
        textArea.setEditable(false);
        textArea2.setEditable(false);


    }




}