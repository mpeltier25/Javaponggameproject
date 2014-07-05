import java.net.*;
import java.io.*;
public class Sender {
private int toPort;
private InetAddress toAdr;
private DatagramSocket socket;
private revamppong gui;
private Board gA;	
    public  Sender(InetAddress toAdr, int toPort, revamppong gui, Board gA) {
    
        this.toAdr = toAdr;
        this.toPort = toPort;
        this.gui = gui;
        this.gA = gA;
        try {
            socket = new DatagramSocket();
            gA.setConnected(true);
        }
        catch(SocketException se) {
            gA.disconnect();
        }
    } 
    protected synchronized void sendAway(int x, int y, int l, int c,int l2, int c2,String toSend) {
    
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            
            if(toSend.equals("activ"))
                dos.writeChar('a');
            else if(toSend.equals("inActiv"))
                dos.writeChar('i');
            else if(toSend.equals("stoped")) 
                dos.writeChar('s');
            else if(toSend.equals("resumed"))
                dos.writeChar('r');
            else if(toSend.equals("hit"))
                dos.writeChar('h');
            else if(toSend.equals("ball")) {
                dos.writeChar('b');
                dos.writeInt(x);
                dos.writeChar('\t');
                dos.writeInt(y);
            } //slut else if
            else if(toSend.equals("player")) {
                dos.writeChar('p');
                dos.writeInt(x);
                dos.writeChar('\t');
                dos.writeInt(y);
            }
            else if(toSend.equals("newGame"))
                dos.writeChar('n');
            else if(toSend.equals("gameOver")) 
                dos.writeChar('g');
            else if(toSend.equals("quit"))
                dos.writeChar('q');
            else if(toSend.equals("lifeAndScore")){
            dos.writeChar('l');
            dos.writeInt(l);
            dos.writeChar('\t');
            dos.writeInt(c);
            dos.writeChar('\t');
            dos.writeInt(l2);
            dos.writeChar('\t');
            dos.writeInt(c2);
            } 
            dos.flush();
            dos.close();
            byte[]data = baos.toByteArray();
            DatagramPacket packet = new DatagramPacket(data,
            data.length, toAdr, toPort);
            socket.send(packet);
        } 
        catch(IOException ioe) {
        } 
    }
    public void closeSendSocket() {
        
        try {
        socket.close();
        }
        catch(Exception e) {
        }
    } 
}