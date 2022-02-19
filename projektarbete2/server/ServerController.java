package server;

import javax.swing.*;


//the server will have a GUI.

//nånting fel med servern. GUI visas inte av någon anledning.


public class ServerController extends JFrame {


    private ServerModel serverModel;
    private ServerView serverView;

    public ServerController(ServerModel serverModel){

        super("Server");
        this.serverModel = serverModel;
        this.serverView = new ServerView(serverModel);

        serverView.textArea.append("Server up and running on port "+serverModel.getPort()+"\n");
        serverView.textArea.append("Local Server IP address is "+serverModel.getIpAddress());

        add(serverView.serverPanel);

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }



    public static void main(String[] args) {


        ServerModel serverModel = new ServerModel();

        ServerController serverController = new ServerController(serverModel);

        //den fastnar i servermodel while loop. du måste ge den efteråt
        serverModel.startServer();



    }




}
