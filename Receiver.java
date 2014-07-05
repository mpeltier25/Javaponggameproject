
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
public class Receiver extends Thread {

    private Board gA;
    private DatagramSocket socket;
    private Player p2, p1;
    private int myPort;
    private Ball ball;
    private revamppong gui;
    private boolean looping = false;
    private String portSettingsText = "Having trouble with port/firewall settings";
    private final static int MAX_PACKET_SIZE = 8192;
    public Receiver(Board gA, revamppong gui, int myPort, Player p1, Player p2, Ball ball) {
        this.myPort = myPort;
        this.p2 = p2;
        this.p1 = p1;
        this.gA = gA;
        this.ball = ball;
        this.gui = gui;
        try {
            socket = new DatagramSocket(myPort);
            looping = true;
            gA.setConnected(true);
        } 
        catch(SocketException se) {
            JOptionPane.showMessageDialog(null, portSettingsText,
            "Port settings problem", JOptionPane.INFORMATION_MESSAGE);
            gA.disconnect();
        }
    
    }
    public void run() {
        byte[] buffer = new byte[MAX_PACKET_SIZE];
        while(looping) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            char t;
            try {
            socket.receive(packet);
            InputStream in = new ByteArrayInputStream(packet.getData(), packet.getOffset(),
            packet.getLength());
            DataInputStream din = new DataInputStream(in);
            
            char sent = din.readChar();
            if(sent == 'a'){
                gA.setActive(false);
                gui.setTitle(gui.getGameTitle() + "Connected to " + gui.getHost());
                gA.sendStatus("inActiv");
                gA.reset();
            }
            else if(sent == 'i') {
                gA.setActive(true);
                gui.setTitle(gui.getGameTitle() + "Connected to " + gui.getHost() + " pressstart");
                gA.reset();
            }
            else if(sent == 'g')
                gA.setGameOver();
            else if(sent == 'b') {
                int x = din.readInt();
                t = din.readChar();
                int y = din.readInt();
                ball.setXPos(gA.getXMax() - x);
                ball.setYPos(y);
            }
            else if(sent == 'p') {
                int x = din.readInt();
                t = din.readChar();
                int y = din.readInt();
                p2.setXpos(x);
                p2.setYpos(y);
            }
            else if(sent == 'l') {
                int l = din.readInt();
                t = din.readChar();
                int c = din.readInt();
                t = din.readChar();
                int l2 = din.readInt();
                t = din.readChar();
                int c2 = din.readInt();
                p1.setLife(l);
                gui.setPlayerOneLife(String.valueOf(l));
                gui.setPlayerTwoLife(String.valueOf(l2));
            }
            din.close();
            gA.paintGameComponents();
            } 
            catch(IOException ioe) {
                gA.disconnect();
            }
        }          
    }
    public void closeReceiveSocket() {
        try {
        socket.close();
        }
        catch(Exception e){
        }
    }
}