import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI2 extends JFrame {
    private JPanel mainPanel;
    private JPanel ConnectPanel;
    private JPanel ChatPanel;
    private JFormattedTextField formattedTextField;
    private JButton connectButton;
    private JTextField messageField;
    private JTextArea textArea;
    private JButton sendMessageButton;
    private CardLayout cl = (CardLayout) mainPanel.getLayout();     //denna layout tillåter att man går från connect window till chat window på ett smidigt sätt

    private boolean buttonPressed;
    private boolean connected;



    public ClientGUI2() {

        super("client");
        add(mainPanel);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        buttonPressed = false;
        connected = false;

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client3.username= formattedTextField.getText(); //sätt client username till det username som client skrev i connect window.
                connected = true;
                cl.show(mainPanel,"Card2");
            }
        });


        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                buttonPressed = true;
                messageField.setText("");   //ta bort texten från message field som client skrivit. Detta så att client kan skriva ett nytt message.


            }
        });
    }


    //ska dessa funktioner vara i controller?

    public boolean sendButtonPressed(){
        return buttonPressed;
    }  //returnerar ifall send knappen har tryckts

    public void unPressButton(){
        buttonPressed = false;
    }  //sätter send knappen till falsk så att man kan skicka ett nytt meddelande

    public boolean hasConnected(){
        return connected;
    } //kollar ifall användaren har tryckt på "connect"

    public String getMessage() {
        return messageField.getText();
    } //hämtar meddelandet som klienten har skrivit i messagefield

    public void writeToTextArea(String message){
        textArea.setText(message+"\n");
    }  //skriver till textArea, dvs där alla meddelanden visas.

    public void clearTextArea(){
        textArea.setText("");
    } //tar bort all text från textarea, så att ny text kan visas.

    public static void main (String[] args){

        new ClientGUI2();

    }


}
