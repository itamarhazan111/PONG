import java.awt.*;

public class Score extends Rectangle{
    private static int GAME_WIDTH;
    private static int GAME_HEIGHT;
    private int player1;
    private int player2;
    Score(int GAME_WIDTH,int GAME_HEIGHT){
        Score.GAME_HEIGHT=GAME_HEIGHT;
        Score.GAME_WIDTH=GAME_WIDTH;
    }
    public void addPointForPlayer1(){
        this.player1++;
    }
    public void addPointForPlayer2(){
        this.player2++;
    }

    public int getPlayer1() {
        return this.player1;
    }
    public int getPlayer2() {
        return this.player2;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Consolas",Font.PLAIN,60));
        g.drawLine(GAME_WIDTH/2,0,GAME_WIDTH/2,GAME_HEIGHT);
        g.drawString(String.valueOf(this.player1/10)+String.valueOf(this.player1%10),(GAME_WIDTH/2)-85,50);
        g.drawString(String.valueOf(this.player2/10)+String.valueOf(this.player2%10),(GAME_WIDTH/2)+20,50);
    }

}
