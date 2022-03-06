package client;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * ClientController class handles the Listeners for the clientview.
 *
 * @Author Mirco Ghadri, Ramza Josoph, Valeria Nafuna, Zakariya Omar, "Group 3"
 *
 * @version 1.0 3/2/2022
 */
public class ClientController extends JFrame {

    private final ClientModel clientModel;
    private final ClientView clientView;
    private final CardLayout cl;
    private boolean sound;


    /**
     * Class constructor that adds listeners to the components in the Clientview.
     */
    public ClientController() {
        super("client");

        this.clientModel = new ClientModel();
        this.clientView = new ClientView();

        add(clientView.getmainPanel());

        cl = (CardLayout) clientView.getmainPanel().getLayout();

        sound = true;

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);

        clientView.getSoundItemOn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getSoundItemOn()) {
                    sound = true;
                    clientModel.setsound(true);
                }
            }
        });

        clientView.getSoundItemOff().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getSoundItemOff()) {
                    sound = false;
                    clientModel.setsound(false);
                }

            }
        });

        clientView.getColorItem1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getColorItem1()) {
                    clientView.gettextArea().setBackground(new Color(124, 172, 245));
                }

            }
        });

        clientView.getColorItem2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getColorItem2()) {
                    clientView.gettextArea().setBackground(new Color(242, 232, 169));
                }

            }
        });

        clientView.getColorItem3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getColorItem3()) {
                    clientView.gettextArea().setBackground(new Color(255, 255, 255));
                }

            }
        });

        clientView.getOnItemTS().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getOnItemTS()) {

                    clientView.setTime(true);
                }
            }
        });

        clientView.getOffItemTS().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getOffItemTS()) {
                    clientView.setTime(false);
                }
            }
        });

        clientView.getAboutItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getAboutItem()) {
                    String aboutMsg = "Simple Chat Application with Java\nContributers:\nMirco Ghadir\nRamza Josoph\nValeria Nafuna\nZakariya Omar\nDAT055 2022-03-07";
                    String title = "About";
                    JOptionPane.showMessageDialog(null, aboutMsg, title, JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        clientView.getSaveItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getSaveItem()) {
                    clientModel.save();
                }
            }
        });

        clientView.getHelpItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clientView.getHelpItem()) {
                    Desktop d = Desktop.getDesktop();
                    try {
                        d.browse(new URI("https://github.com/ksedix/Java-Chat-Application/blob/main/README.md"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        clientView.getconnectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (clientModel.getSocket() == null) {
                    clientModel.connectToServer();
                }

                clientView.setClientModel(clientModel);

                if (clientModel.getSocket() == null) {
                    clientView.getconnectionError().setText("Server is not running.");
                } else if (clientView.getformattedTextField().getText().equals("")) {
                    clientView.getconnectionError().setText("Please enter a valid username.");

                    String userdirectory = System.getProperty("user.dir");
                    if (sound == true) {
                        AudioInputStream audio = null;

                        try {
                            audio = AudioSystem.getAudioInputStream(new File("projektarbete2/ErrorSound.wav"));
                        } catch (UnsupportedAudioFileException er) {
                            er.printStackTrace();
                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        Clip clip = null;
                        try {
                            clip = AudioSystem.getClip();
                        } catch (LineUnavailableException er) {
                            er.printStackTrace();
                        }
                        try {
                            clip.open(audio);
                        } catch (LineUnavailableException er) {
                            er.printStackTrace();
                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        clip.start();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        clip.stop();

                    }


                } else if (clientView.getformattedTextField().getText().length() > 15) {
                    clientView.getconnectionError().setText("Username can't be longer than 15 characters");
                    String userdirectory = System.getProperty("user.dir");
                    if (sound == true) {
                        AudioInputStream audio = null;

                        try {
                            audio = AudioSystem.getAudioInputStream(new File("projektarbete2/ErrorSound.wav"));
                        } catch (UnsupportedAudioFileException er) {
                            er.printStackTrace();
                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        Clip clip = null;
                        try {
                            clip = AudioSystem.getClip();
                        } catch (LineUnavailableException er) {
                            er.printStackTrace();
                        }
                        try {
                            clip.open(audio);
                        } catch (LineUnavailableException er) {
                            er.printStackTrace();
                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        clip.start();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        clip.stop();

                    }
                } else {

                    cl.show(clientView.getmainPanel(), "Card2");
                    clientModel.setUsername(clientView.getformattedTextField().getText());
                    clientModel.sendUserName(); // Connect button sends username to Server ---------------------------------
                    clientModel.sendMessage("User " + clientView.getformattedTextField().getText() + " has connected to the server");
                    setTitle("Client - " + clientModel.getUsername());

                    String userdirectory = System.getProperty("user.dir");
                    if (sound == true) {
                        AudioInputStream audio = null;

                        try {
                            audio = AudioSystem.getAudioInputStream(new File("projektarbete2/ConnectedSound.wav"));
                        } catch (UnsupportedAudioFileException er) {
                            er.printStackTrace();
                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        Clip clip = null;
                        try {
                            clip = AudioSystem.getClip();
                        } catch (LineUnavailableException er) {
                            er.printStackTrace();
                        }
                        try {
                            clip.open(audio);
                        } catch (LineUnavailableException er) {
                            er.printStackTrace();
                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        clip.start();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        clip.stop();

                    }

                }
            }
        });


        clientView.getsendMessageButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                clientModel.sendMessage(clientModel.getUsername() + ": " + clientView.getmessageField().getText());
                clientView.getmessageField().setText("");

            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the client?");
                if (input == JOptionPane.YES_NO_OPTION) {
                    if (clientModel.getSocket() != null) {
                        clientModel.sendMessage("User " + clientModel.getUsername() + " disconnected from the server");
                    }

                    System.exit(0);
                }
            }


        });
    }

    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        clientController.listenForMessage();

    }


    /**
     * This method is the main run method for the client, it has a continual loop for the client to
     * continuously read the incoming messages and get a sound notification.
     */
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    if (clientModel.readIncoming()) {
                        clientView.displayMessage();
                        if (sound == true) {
                            AudioInputStream audio = null;

                            try {
                                audio = AudioSystem.getAudioInputStream(new File("projektarbete2/MessageSound.wav"));
                            } catch (UnsupportedAudioFileException er) {
                                er.printStackTrace();
                            } catch (IOException er) {
                                er.printStackTrace();
                            }
                            Clip clip = null;
                            try {
                                clip = AudioSystem.getClip();
                            } catch (LineUnavailableException er) {
                                er.printStackTrace();
                            }
                            try {
                                clip.open(audio);
                            } catch (LineUnavailableException er) {
                                er.printStackTrace();
                            } catch (IOException er) {
                                er.printStackTrace();
                            }
                            clip.start();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            clip.stop();

                        }

                        clientView.displayUsers();
                    }
                }
            }
        }).start();
    }

}
