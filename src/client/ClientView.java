package client;

import javax.swing.*;
import java.util.ArrayList;

import message.Message;

/**
 * The ClientView displays the information that the client gets from the server in the chat window.
 *
 * @author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 * @version 1.0 3/2/2022
 */

public class ClientView {

    private JPanel mainPanel;
    private JPanel ConnectPanel;
    private JPanel ChatPanel;
    private JFormattedTextField formattedTextField;
    private JButton connectButton;
    private JTextField messageField;
    private JTextArea textArea;
    private JButton sendMessageButton;
    private JLabel connectionError;
    private JTextArea textArea1;

    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu helpMenu;
    private JMenu colorMenu;
    private JMenu soundMenu;
    private JMenu TSMenu;

    private JMenuItem colorItem1;
    private JMenuItem colorItem2;
    private JMenuItem soundItemOn;
    private JMenuItem soundItemOff;
    private JMenuItem offItemTS;
    private JMenuItem onItemTS;
    private JMenuItem colorItem3;
    private JMenuItem helpItem;
    private JMenuItem aboutItem;
    private JMenuItem saveItem;

    private boolean time;

    private ClientModel clientModel;


    /**
     * Class constructor that provides the ClientView with a clientModel so that it can display data and fetch data from the clientModel.
     * Makes the text area which displays all the messages uneditable.
     * Sets lineWrap to true so that long messages will break on the chat window instead of creating a horizontal scroll policy.
     */

    public ClientView() {
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea1.setEditable(false);
        colorMenu.add(colorItem1);
        colorMenu.add(colorItem2);
        colorMenu.add(colorItem3);
        soundMenu.add(soundItemOff);
        soundMenu.add(soundItemOn);
        TSMenu.add(offItemTS);
        TSMenu.add(onItemTS);

    }

    /**
     * method to set the message time visible or not
     *
     * @param t boolean to set time
     */
    public void setTime(boolean t) {
        time = t;
    }

    /**
     * Displays all the messages in the client chatlog in the client chat window.
     */
    public void displayMessage() {
        ArrayList<Message> chatLog = clientModel.getChatLog();
        textArea.setText("");
        for (Message msg : chatLog) {
            if (time == true) {
                textArea.append(msg.toString() + "\n");
            } else {
                String m = msg.toString().substring(msg.toString().indexOf(">") + 1);
                textArea.append(m + "\n");
            }

        }
    }

    /**
     * reads the online users message that server has sent to
     * clients input-stream and displays it in the  window.
     */
    public void displayUsers() {
        ArrayList<String> users = clientModel.getOnlineUsers();
        textArea1.setText("");
        for (String user : users) {
            textArea1.append(user + "\n");
        }
    }

    /**
     * method for accessing the mainJpanel
     *
     * @return mainPanel
     */
    public JPanel getmainPanel() {
        return this.mainPanel;
    }

    /**
     * for accessing the  textfield
     *
     * @return formattedTextField
     */
    public JFormattedTextField getformattedTextField() {
        return this.formattedTextField;
    }

    /**
     * used for accessing the button when adding listener to the button
     *
     * @return a button
     */
    public JButton getconnectButton() {
        return this.connectButton;
    }

    /**
     * used to access Textfield to get the users input
     *
     * @return messageField
     */
    public JTextField getmessageField() {
        return this.messageField;
    }

    /**
     * used to access button when adding listener to send user message
     *
     * @return button
     */
    public JButton getsendMessageButton() {
        return this.sendMessageButton;
    }

    /**
     * accesses the label used to send message notice to user
     *
     * @return a label
     */
    public JLabel getconnectionError() {
        return this.connectionError;
    }

    /**
     * used to access the textarea for displaying received messages
     *
     * @return textarea
     */
    public JTextArea gettextArea() {
        return this.textArea;
    }

    /**
     * used to access menu for changing the color of the textarea.
     *
     * @return colorItem
     */
    public JMenuItem getColorItem1() {
        return this.colorItem1;
    }

    /**
     * used to access menu for changing the color of the textarea.
     *
     * @return colorItem
     */
    public JMenuItem getColorItem2() {
        return this.colorItem2;
    }

    /**
     * used to access menu for changing the color of the textarea.
     *
     * @return colorItem
     */
    public JMenuItem getColorItem3() {
        return this.colorItem3;
    }

    /**
     * accesses menu when setting timestamp visibility
     *
     * @return menu item
     */
    public JMenuItem getOnItemTS() {
        return this.onItemTS;
    }

    /**
     * accesses menu when setting off timestamp visibility
     *
     * @return menu item
     */
    public JMenuItem getOffItemTS() {
        return this.offItemTS;
    }

    /**
     * accesses menu when setting off sound notification
     *
     * @return menu item
     */
    public JMenuItem getSoundItemOff() {
        return this.soundItemOff;
    }

    /**
     * accesses menu when setting on sound notification
     *
     * @return menu item
     */
    public JMenuItem getSoundItemOn() {
        return this.soundItemOn;
    }

    /**
     * accesses the menu when adding what the project is about
     *
     * @return aboutItem
     */
    public JMenuItem getAboutItem() {
        return this.aboutItem;
    }

    /**
     * accesses the menu when adding the help option
     *
     * @return HelpItem
     */
    public JMenuItem getHelpItem() {
        return this.helpItem;
    }

    /**
     * accesses the menu when adding the save option
     *
     * @return saveItem
     */
    public JMenuItem getSaveItem() {return this.saveItem;}

    /**
     * method for initialising the clientmodel
     *
     * @param clientmodel initialises clientmodel
     */
    public void setClientModel(ClientModel clientmodel) {
        this.clientModel = clientmodel;
    }

}
