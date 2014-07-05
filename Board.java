
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
public class Board extends JPanel  {
    
    private boolean isActive = false, isPlaying = false, connected = false, gameOver = false;
    public Thread receiveActivity, ballActivity, playerOneActivity, playerTwoActivity;
    private int xMax, yMax;
    private revamppong gui;
    private Ball ball;	
    private Player p1, p2;
    private Sender sender;
    private Receiver receiver;
    public Board(revamppong gui) { 
        
        setBackground(Color.white);
        this.gui = gui;
        addKeyListener(kl);
    }
    public void sendStatus(String s) {
        
        if(connected)
            sender.sendAway(p1.getXpos() + xMax - p1.getWidth(), p1.getYpos(), p2.getLife(), p2.getPoints(), p1.getLife(), p1.getPoints(), s);
    }
    public void connect(int localPort, InetAddress remoteAdr, int remotePort, int xMax, int yMax) {
        
        this.xMax = xMax;
        this.yMax = yMax;
        p1 = new Player(this, 0);
        p2 = new Player(this, xMax - p1.getWidth());
        sender = new Sender(remoteAdr, remotePort, gui, this);
        ball = new Ball(gui, this, p1, p2, sender);
        ballActivity = new Thread(ball);
        receiver = new Receiver(this, gui, localPort, p1, p2, ball);
        receiveActivity = new Thread(receiver);
        receiveActivity.start();
        sendStatus("activ");
        isPlaying = false;
    }
    public void disconnect(){
        
        if(connected) {
            gui.setTitle(gui.getGameTitle() + "disconnect, terminate game");
            System.exit(-1);
        } 
    }
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        if(ball != null) {
            g.setColor(ball.getColor());
            g.fillOval(ball.getXPos() - ball.getRadie(), ball.getYPos() - 
            ball.getRadie(), ball.getWidth(), ball.getHeight());
            g.setColor(p1.getColor());
            g.fillRect(p1.getXpos(), p1.getYpos(), p1.getWidth(), p1.getHeight());
            g.setColor(p2.getColor());
            g.fillRect(p2.getXpos(), p2.getYpos(), p2.getWidth(), p2.getHeight());
        }
    }
    public void startGame() {
        
        if(!gameOver && connected){
            ball.setLooping(true);
            if(!ballActivity.isAlive()) 
                ballActivity.start();
            setBackground(Color.white);
            isPlaying = true;
        } 
    }
    public void setGameOver() {
        
        if(isActive) {
            sendStatus("gameOver");
            ball.setLooping(false);
        }
        gui.setTitle(gui.getGameTitle() +"                  GAME OVER");
        System.exit(0);
        gameOver = true;
        
    }
    public void newGame() {
        
        if(connected){
            startGame();
        }
    }
    public void reset() {
        p1.setLife(5);
        p1.setYpos(yMax / 2 - p1.getHeight() / 2);
        p1.setColor(Color.green);
        gui.setPlayerOneLife(String.valueOf(p1.getLife()));
        p2.setLife(5);
        p2.setYpos(yMax / 2 - p2.getHeight() / 2);
        p2.setColor(Color.green);
        gui.setPlayerTwoLife(String.valueOf(p2.getLife()));
        if(isActive)
            ball.setXPos(ball.getRadie() + p1.getWidth() + 1);
        else
            ball.setXPos(xMax - ball.getRadie() - p2.getWidth() - 1);
        ball.setYPos(yMax / 2);
        gui.setConnectButton(false); 
    }
    KeyListener kl = new KeyAdapter() {   
        public void  keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP)
                p1.setYpos(Math.max(0, p1.getYpos() - p1.getStep()));
            else if(e.getKeyCode() == KeyEvent.VK_DOWN)
                p1.setYpos(Math.min(yMax - p1.getHeight(), p1.getYpos() + p1.getStep()));
            sendStatus("player");
            repaint();
        } 
    };
    public int getYmax() {
        
        return yMax;
    }
    public int getXMax() {
            
        return xMax;
    }
    public  void paintGameComponents() {
        
        repaint();
    }
    public void setConnected(boolean s) {
        
        connected = s;
    }
    public boolean connected() {
        
        return connected;
    }
    public boolean activePlayer() {
        
            return isActive;
    }
    
    public void setActive(boolean b) {
        
        isActive=b;
        
    }
    public boolean playing() {
        
        return isPlaying;
    }
}