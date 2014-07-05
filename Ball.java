
import javax.swing.*;
import java.awt.*;

public class Ball extends JPanel implements Runnable {
        
    private int frequency = 100;
    private int radie;
    private int height;	
    private int width;	
    private int xPos;
    private int yPos;
    private int xStep;
    private int yStep;
    private int speed;
    private final int initSpeed = 5;
    private final int maxSpeed = 30;
    private boolean looping=false;	
    private revamppong gui;
    private Board gA;
    private Player p1,p2;
    private Sender sender;
    private Color color = Color.blue;	
    public Ball(revamppong gui, Board gA, Player p1, Player p2, Sender sender) {
        
        this.gA = gA;
        this.p1 = p1;
        this.p2 = p2;
        this.gui = gui;
        this.sender = sender;
        radie = gA.getYmax() / 25;
        height = width = radie * 2;	   
        xStep = yStep =speed = initSpeed;
    }	
    public void run() {
        
        while(true) {
        try {
            Thread.sleep(frequency);
            synchronized(this) {
            while (!looping)
                wait();
            } 
        }
        catch (InterruptedException e) {	
        }
        handleBall();
        gA.paintGameComponents();
        }
    }
    public synchronized void  setLooping(boolean b) {
        
        looping = b;
            if (looping)
            notify();
    }
    public Color getColor() {
        
        return color;
    }
    public void setFrequency(int i) {
        
        frequency = i;
    }
  
    public int getRadie() {
        
        return radie;
    }
    public void setXPos(int xPos) {
        
        this.xPos = xPos;
    }
    public void setYPos(int yPos) {
        
        this.yPos = yPos;
    }
    
    public int getXPos() {
        
        return xPos;
    }
    public int getYPos() {
        
        return yPos;
    }
    public int getHeight() {
        
        return height;
    }
    public int getWidth() {
        
        return width;
    }
    public int getInitSpeed() {
        
        return initSpeed;
    }
    public int getMaxSpeed() {
        
        return maxSpeed;
    }
    public int getXStep() {
        
        return xStep;
    }
    public void setXStep(int xStep) {
        
        this.xStep = xStep;
    }
    public int getYStep() {
        
        return yStep;
    }
    public void setYStep(int yStep) {
        
        this.yStep = yStep;
    }
    public void setSpeed(int speed) {
        
        this.speed = speed;
    }
    protected void handleBall() {
    
        if(xPos - radie <= p1.getWidth()) {
            if(yPos < p1.getYpos() || yPos > p1.getYpos() + p1.getHeight()) {
                p1.setLife(p1.getLife() - 1);
                if(p1.getLife() <= 0) { 
                    gA.setGameOver();
                    p1.setLife(0);
                }
                gui.setPlayerOneLife(String.valueOf(p1.getLife()));
                if(speed < maxSpeed)
                    speed += 2;
            }
            else {
                sender.sendAway(xPos, yPos, p2.getLife(), p2.getPoints(), p1.getLife(), p1.getPoints(), "hit");
                if(speed < maxSpeed)
                    speed += 1;  
            }
        gA.sendStatus("lifeAndScore");
        xStep = speed;
        } 
        else if(xPos + radie >= gA.getXMax() - p2.getWidth()) {
            if(yPos < p2.getYpos() || yPos > p2.getYpos() + p2.getHeight()) {
                p2.setLife(p2.getLife() - 1);
                if(p2.getLife() <= 0) {
                    gA.setGameOver();
                    p2.setLife(0);
                }
                gui.setPlayerTwoLife(String.valueOf(p2.getLife()));
                if(speed < maxSpeed)
                    speed += 2 ;
            }
            else {
                sender.sendAway(xPos, yPos, p2.getLife(), p2.getPoints(), p1.getLife(), p1.getPoints(),"hit"); //send info on hit
                if(speed < maxSpeed)	
                    speed += 1;
            }
        gA.sendStatus("lifeAndScore"); 
        xStep =- speed;
        }
    
        if(yPos-radie <= 0 || yPos+radie >= gA.getYmax())
            yStep =- yStep;
        xPos += xStep;
        yPos += yStep;
        if(xPos < radie)
            xPos = radie;
            else if(xPos > gA.getXMax() - radie)
            xPos = gA.getXMax() - radie;
        if(yPos < radie)
            yPos = radie;
        else if(yPos > gA.getYmax() - radie)
            yPos = gA.getYmax() - radie;
        
        
        else if(xPos  <  (radie + p1.getWidth()) && yPos > p1.getYpos() && yPos < p1.getYpos() + p1.getHeight())
            xPos = radie + p1.getWidth();
        else if(xPos>gA.getXMax() - radie - p2.getWidth() &&  yPos > p2.getYpos() && yPos < p2.getYpos() + p2.getHeight())
            xPos = gA.getXMax() - radie - p2.getWidth(); 
        sender.sendAway(xPos, yPos, 0, 0, 0, 0, "ball");
    } 
            
}


