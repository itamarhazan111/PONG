import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable{
    private static final int GAME_WIDTH=1000;
    private static final int GAME_HEIGHT=(int) (GAME_WIDTH*(0.5555));
    private static final Dimension SCREEN_SIZE=new Dimension(GAME_WIDTH,GAME_HEIGHT);
    private static final int BALL_DIAMETER=20;
    private static final int PADDLE_WIDTH=25;
    private static final int PADDLE_HEIGHT=100;
    private Thread gameThread;
    private Image image;
    private Graphics graphics;
    private Random random;
    private Paddle paddle1;
    private Paddle paddle2;
    private Ball ball;
    private Score score;

    private boolean player1Win=false;
    private boolean player2Win=false;

    GamePanel(){
        newPaddles();;
        newBall();
        score=new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread=new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random=new Random();
        ball=new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPaddles(){
        paddle1=new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2=new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);

    }
    public void paint(Graphics g){
        image=createImage(getWidth(),getHeight());
        graphics=image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }
    public void draw(Graphics g){
        if(!player1Win&&!player2Win) {
            paddle1.draw(g);
            paddle2.draw(g);
            ball.draw(g);
            score.draw(g);

        }else {
            Font largerFont = new Font("ink free",Font.BOLD,75);
            g.setFont(largerFont);
        }
        if (player1Win) {
            g.setColor(Color.blue);
            int stringWidth = graphics.getFontMetrics().stringWidth("player1 win");
            g.drawString("player1 win", GAME_WIDTH / 2 - stringWidth / 2, GAME_HEIGHT / 2);
        } else if (player2Win) {
            g.setColor(Color.RED);
            int stringWidth = graphics.getFontMetrics().stringWidth("player2 win");
            g.drawString("player2 win", GAME_WIDTH / 2 - stringWidth / 2, GAME_HEIGHT/ 2);
        }

    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollision(){
        //bounce ball off the top or bottom
        if(ball.getY()<=0)
           ball.setYDirection(-ball.getYVelocity());
       if(ball.getY()>=GAME_HEIGHT-BALL_DIAMETER)
            ball.setYDirection(-ball.getYVelocity());
        //bounce ball off paddles
        if (ball.intersects(paddle1)){
            ball.setXVelocity(Math.abs(ball.getXVelocity()));
            ball.setXVelocity(ball.getXVelocity()+1);
            if(ball.getYVelocity()>0)
                ball.setYVelocity(ball.getYVelocity()+1);
            else ball.setYVelocity(ball.getYVelocity()-1);
            ball.setXDirection(ball.getXVelocity());
            ball.setYDirection(ball.getYVelocity());
        }
        if (ball.intersects(paddle2)){
            ball.setXVelocity(Math.abs(ball.getXVelocity()));
            ball.setXVelocity(ball.getXVelocity()+1);
            if(ball.getYVelocity()>0)
                ball.setYVelocity(ball.getYVelocity()+1);
            else ball.setYVelocity(ball.getYVelocity()-1);
            ball.setXDirection(-ball.getXVelocity());
            ball.setYDirection(ball.getYVelocity());
        }
        //bounce paddle off the top or bottom
        if(paddle1.getY()<=0)
            paddle1.setY(0);
        if(paddle1.getY()>=(GAME_HEIGHT-PADDLE_HEIGHT))
            paddle1.setY(GAME_HEIGHT-PADDLE_HEIGHT);
        if(paddle2.getY()<=0)
            paddle2.setY(0);
        if(paddle2.getY()>=(GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.setY(GAME_HEIGHT-PADDLE_HEIGHT);
        //bounce ball off the left or right
        if(ball.getX()<=0) {
            score.addPointForPlayer2();
            newPaddles();
            newBall();
            //System.out.println(score.getPlayer2());
        }
        if(ball.getX()>=GAME_WIDTH) {
            score.addPointForPlayer1();
            newPaddles();
            newBall();
            //System.out.println(score.getPlayer1());
        }
    }
    public void checkWin(){
        if (score.getPlayer1()>=11){
            player1Win=true;
        }else if (score.getPlayer2()>=11) {
            player2Win=true;
        }

    }

    public void run(){
        long lastTime=System.nanoTime();
        double amountOfTicks=60.0;
        double ns=1000000000/amountOfTicks;
        double delta=0;
        while (!player2Win&&!player1Win){
            long now=System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=now;
            if(delta>=1){
                move();
                checkCollision();
                checkWin();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
