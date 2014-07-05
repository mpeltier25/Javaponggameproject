
import java.awt.Color;
public class Player{
    private  int xPos;
    private int yPos;
    private int width = 4;
    private int height = 32;
    private int step;
    private int life = 5;
    private int points = 0;
    private Color color = Color.magenta;
    public Player(Board gA,int xPos) {
        this.xPos = xPos;
        yPos = gA.getYmax() / 2 - height / 2;
        step = gA.getYmax() / 25;
    } 
    public Color getColor() {
        
        return color;
    }
    public void setColor(Color c) {
        
        color = c;
    }
    public int getXpos() {
        
        return xPos;
    }
    public int getYpos() {
        
        return yPos;
    }
    public int getWidth() {
        
        return width;
    }
    public int getHeight() {
        
        return height;
    }
    
    public int getPoints() {
        
        return points;
    }
    public int getLife() {
        
        return life;
    }
    public void setLife(int life) {
        
        this.life=life;
    }
    public void setYpos(int yPos) {
        
        this.yPos=yPos;
    }
    public void setXpos(int xPos) {
        
        this.xPos=xPos;
    }
    public int getStep() {
        
        return step;
    }
}