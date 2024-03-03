import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    private Random random;
    private int yVelocity;
    private int xVelocity;
    private int initialSpeed=2;
    Ball(int x,int y,int width,int height){
        super(x,y,width,height);
        random=new Random();
        int xRandomDirection=random.nextInt(2);
        if(xRandomDirection==0)
            xRandomDirection--;
        setXDirection(xRandomDirection*initialSpeed);
        int yRandomDirection=random.nextInt(2);
        if(yRandomDirection==0)
            yRandomDirection--;
        setYDirection(yRandomDirection*initialSpeed);
    }
    public void setXDirection(int xRandomDirection){
        xVelocity=xRandomDirection;
    }
    public void setYDirection(int yRandomDirection){
        yVelocity=yRandomDirection;
    }
    public void move(){
        x+=xVelocity;
        y+=yVelocity;
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x,y,width,height);
    }
    public int getYVelocity(){
        return this.yVelocity;
    }
    public int getXVelocity(){
        return this.xVelocity;
    }
    public void setYVelocity(int y){
        this.yVelocity=y;
    }
    public void setXVelocity(int x){
        this.xVelocity=x;
    }
}
