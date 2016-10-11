import java.awt.*;
import java.awt.event.*;

public class Player
{
    public static final int SPEED = 1;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 70;
    public static final short LEFTPLAYER = 1;
    public static final short RIGHTPLAYER = 2;
    public static final short AI = 3;

    private int x;
    private int y;
    private int ya = 0;
    private short playerType;
    private int score = 0;
    private Wall topWall;
    private Wall bottomWall;
    private Ball ball;

    public Player(int x, int y, Wall topWall, Wall bottomWall, short playerType)
    {
        this.x = x;
        this.y = y;
        this.playerType = playerType;
        this.topWall = topWall;
        this.bottomWall = bottomWall;
    }

    public void paint(Graphics g)
    {
        Graphics2D page = (Graphics2D) g;
        page.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);

        if (y <= (topWall.getY() + Wall.WALLHEIGHT))
        {
            y = topWall.getY() + Wall.WALLHEIGHT;
            ya = 0;
        }
        else if ((y + HEIGHT) >= bottomWall.getY())
        {
            y = bottomWall.getY() - HEIGHT;
            ya = 0;
        }

        page.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void movePlayer()
    {
        // AI tracking:
        if (playerType == AI)
        {
            if (ball.getY() < y + (HEIGHT / 2))
                ya = -SPEED;
            else if (ball.getY() > y + (HEIGHT / 2))
                ya = SPEED;
            else
                ya = 0;
        }
        y += ya;
    }

    public void keyPressed(KeyEvent e)
    {
        if (playerType == RIGHTPLAYER && ((e.getKeyCode() == KeyEvent.VK_UP)
                                          || (e.getKeyCode() == KeyEvent.VK_DOWN)))
        {
            if (e.getKeyCode() == KeyEvent.VK_UP)
                ya = -SPEED;
            else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                ya = SPEED;
        }
        else if (playerType == LEFTPLAYER)
        {
            if (e.getKeyCode() == KeyEvent.VK_W)
                ya = -SPEED;
            else if (e.getKeyCode() == KeyEvent.VK_S)
                ya = SPEED;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        if (playerType == RIGHTPLAYER)
        {
            if (e.getKeyCode() == KeyEvent.VK_UP)
                ya = 0;
            else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                ya = 0;
        }
        if (playerType == LEFTPLAYER)
        {
            if (e.getKeyCode() == KeyEvent.VK_W)
                ya = 0;
            else if (e.getKeyCode() == KeyEvent.VK_S)
                ya = 0;
        }
    }

    public void changeScore(int change)
    {
        score += change;
    }

    /* Getters */
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getScore()
    {
        return score;
    }

    public short getPlayerType()
    {
        return playerType;
    }

    public void setPlayerType(short playerType)
    {
        if (this.playerType != playerType)
            ya = 0;
        this.playerType = playerType;
    }

    public void setBall(Ball ball)
    {
        this.ball = ball;
    }
}