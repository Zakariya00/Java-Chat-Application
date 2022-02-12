import javax.swing.*;

public class ServerController extends JFrame {

    private ServerView serverView;

    public ServerController(ServerView serverView){
        this.serverView = serverView;
        add();
    }

    public static void main(String[] args) {
        new ServerController(new ServerView());
    }

}
