/*
 * Credit for tutorial and code goes to http://www.webpelican.com/internet-programming-1/java-whiteboard/
 * which I used as the basis of this code
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public class revamppong extends JFrame implements ActionListener {
    private String title = "Revamp pong!";
    private String hostname;
    private static final int xMax = 333;
    private static final int yMax = 196;
    private static final int xFrame = 445;
    private static final int yFrame = 330;
    private Board gA;
    private autotext autotext;
    private InetAddress toAdr;
    private int remotePort;
    private int localPort;
    private JLabel playerOneLifeStatus = new JLabel("5");
    private JLabel playerTwoLifeStatus = new JLabel("5", JLabel.CENTER);	
    private static JLabel localPortText = new JLabel("Local Port ", JLabel.LEFT);
    private static JLabel remotePortText = new JLabel("Remote Port ", JLabel.LEFT);
    private static JLabel remoteAdrText = new JLabel("Host", JLabel.LEFT);	
    private JPanel northPan = new JPanel();
    private JPanel southPan = new JPanel();
    private JPanel westPan = new JPanel();		
    private JPanel eastPan = new JPanel();
    private JPanel eastBottomPan = new JPanel();
    private JPanel westBottomPan = new JPanel();
    private JPanel southLeftPan = new JPanel();
    private JPanel southMiddleLeftPan = new JPanel();
    private JPanel southMiddleRightPan = new JPanel();
    private JPanel southRightPan = new JPanel();
    private JButton connect = new JButton("Connect");
    private JButton[] jB = new JButton[1];
    private String[] jBtext = {"Start Game"};
    private JTextField remoteAdrField;
    private JTextField localPortField = new JTextField("2000",1);
    private JTextField remotePortField = new JTextField("2000",1);
    private String unKnownHostText = "Check your Internet connection!";
    private String numberFormatText = "Please enter a number between 0 and 65535!";
    private String localHostText = "Remote and local port can not be the same";
    public revamppong(String hostname, int port) {
    this.hostname = hostname;	
    remoteAdrField = new JTextField(hostname,10);
    remotePort = localPort = port;
    connect.addActionListener(this);	
    northPan.setBackground(Color.red);
    southPan.setBackground(Color.red);
    westPan.setBackground(Color.red);
    eastPan.setBackground(Color.red);
    eastBottomPan.setBackground(Color.red);
    westBottomPan.setBackground(Color.red);
    southMiddleLeftPan.setBackground(Color.red);
    southLeftPan.setBackground(Color.red);
    southMiddleRightPan.setBackground(Color.red);
    southRightPan.setBackground(Color.red);	
    southPan.setBackground(Color.red);
    eastPan.setLayout(new GridLayout(2, 1));
    westPan.setLayout(new GridLayout(2, 1));
    eastBottomPan.setLayout(new BoxLayout(eastBottomPan, BoxLayout.Y_AXIS));	
    westBottomPan.setLayout(new BoxLayout(westBottomPan, BoxLayout.Y_AXIS));
    southLeftPan.setLayout(new GridLayout(2, 1));
    southMiddleLeftPan.setLayout(new GridLayout(2, 1));
    southMiddleRightPan.setLayout(new GridLayout(2, 1));
    westPan.add(westBottomPan);
    eastPan.add(eastBottomPan);
    westBottomPan.add(playerOneLifeStatus);
    eastBottomPan.add(playerTwoLifeStatus);
    southLeftPan.add(remoteAdrText);
    southLeftPan.add(remoteAdrField);
    southMiddleLeftPan.add(remotePortText);
    southMiddleLeftPan.add(remotePortField);
    southMiddleRightPan.add(localPortText);
    southMiddleRightPan.add(localPortField);
    southRightPan.add(connect);
    southPan.add(southLeftPan);
    southPan.add(southMiddleLeftPan);
    southPan.add(southMiddleRightPan);
    southPan.add(southRightPan);
    jB[0] = new JButton();
    jB[0].setText(jBtext[0]);
    jB[0].addActionListener(this);
    northPan.add(jB[0]);    
    Container con = getContentPane();
    con.add(northPan,BorderLayout.NORTH);
    con.add(southPan,BorderLayout.SOUTH);
    con.add(westPan,BorderLayout.WEST);
    con.add(eastPan,BorderLayout.EAST);
    autotext = new autotext(this); 
    gA = new Board(this);
    con.add(gA,BorderLayout.CENTER);
    //addWindowListener(windowClosing);
    setSize(xFrame, yFrame);
    gA.setSize(xMax, yMax);
    show();
    }
    public void setRemoteAdrFieldText(String s) {
        
        if(hostname.equals(""))
            remoteAdrField.setText(remoteAdrField.getText() + s);
    }
    public void actionPerformed(ActionEvent aE) {
        gA.requestFocus();
        if(aE.getSource() == jB[0] && gA.activePlayer()) {
            gA.newGame();			  
        }							     
        else if(aE.getSource() == connect)
            connectPressed();
        }
    private void connectPressed() {  
        if(!gA.connected()) {
            try {
                String theAdr = remoteAdrField.getText().trim();
                toAdr = InetAddress.getByName(theAdr);
                InetAddress localHost = InetAddress.getLocalHost();
                localPort = Integer.parseInt(localPortField.getText());
                remotePort = Integer.parseInt(remotePortField.getText());
                if(localPort <0 || localPort >65535 || remotePort < 0 || remotePort > 65535) {
                    JOptionPane.showMessageDialog(eastPan, numberFormatText,
                    "Bad Port!", JOptionPane.ERROR_MESSAGE);
                }
                else if(toAdr.equals(localHost) && localPort == remotePort 
                    || theAdr.equals("localhost") && localPort == remotePort) {
                    JOptionPane.showMessageDialog(eastPan, localHostText,
                    "Bad local host settings!", JOptionPane.ERROR_MESSAGE);
                }
                else if(remoteAdrField.getText().equals("")) {
                    JOptionPane.showMessageDialog(eastPan, "No host specified!",
                    "Bad host settings!", JOptionPane.ERROR_MESSAGE);
                }
                else 
                    gA.connect(localPort, toAdr, remotePort, xMax, yMax);
            } 
            catch(UnknownHostException une) { // catch and show unknown host exception info
                JOptionPane.showMessageDialog(eastPan, unKnownHostText,
                "Unknown Host!", JOptionPane.ERROR_MESSAGE);
                
            } 
            catch(NumberFormatException nfe) { // catch and show invalid port info
                JOptionPane.showMessageDialog(eastPan,numberFormatText,
                "Invalid Port!", JOptionPane.ERROR_MESSAGE);
            } 
        }
    } 
    public String getHost() {
        
        return toAdr.getHostName();
    } 
    public void setConnectButton(boolean b) {
        
        connect.setEnabled(b);
    }
    public String getGameTitle() {
        
        return title;
    }
    public void setGameTitle(String s) {
        
        title = s;
        setTitle(s);
        
    }
    public void setPlayerOneLife(String s) {
        
        playerOneLifeStatus.setText("    " + s + "");
    } 
    public void setPlayerTwoLife(String s) {
        
        playerTwoLifeStatus.setText("    " + s + "");
    } 
    public void setPlayerLifeColor(String c) {
        
        if(c.equals("reset")) {
            playerOneLifeStatus.setForeground(Color.black);
            playerTwoLifeStatus.setForeground(Color.black);
        }
    }
    public static void main(String[] args) {
        
        String hostname = "";
        int port = 2000;
        new revamppong(hostname, port);
    }
    
}