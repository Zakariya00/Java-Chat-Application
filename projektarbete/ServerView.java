import javax.swing.*;

public class ServerView {
    public JPanel serverPanel;
    private JTextArea textArea;
    private JButton button;

    private ServerModel serverModel;

    public ServerView(ServerModel serverModel){
        this.serverModel = serverModel;
        textArea.setEditable(false);
    }


}
