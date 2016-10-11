import java.awt.*;

public class Ball
{
    public static final int BALLSIZE = 20;
    public static final int SPEED = 1;

    private int x;
    private int y;
    private int xa = -SPEED;
    private int ya = SPEED;
    private static Wall topWall;
    private static Wall bottomWall;
    private static Player player1;
    private static Player player2;

    public Ball(int x, int y, Wall topWall, Wall bottomWall, Player player1,
                Player player2)
    {
        this.x = x;
        this.y = y;
        this.topWall = topWall;
        this.bottomWall = bottomWall;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void paint(Graphics g)
    {
        Graphics2D page = (Graphics2D) g;
        page.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);

        page.fillRect(x, y, BALLSIZE, BALLSIZE);
    }

    public void moveBall() throws InterruptedException
    {
        if (x <= (-BALLSIZE))
        {
            player2.changeScore(Game.SCOREVAL);
            x = (Game.FRAMEWIDTH / 2) - (BALLSIZE / 2);
            xa *= -1;
            y = (Game.FRAMEHEIGHT / 2) - (BALLSIZE / 2);
            return;
        }
        else if (x >= (Game.FRAMEWIDTH + BALLSIZE))
        {
            player1.changeScore(Game.SCOREVAL);
            x = (Game.FRAMEWIDTH / 2) - (BALLSIZE / 2);
            xa *= -1;
            y = (Game.FRAMEHEIGHT / 2) - (BALLSIZE / 2);
            return;
        }

        if ((x + xa <= player1.getX())
            && ((y >= player1.getY())
                && (y <= (player1.getY() + Player.HEIGHT + (BALLSIZE - 1)))))
            xa = SPEED;
        if ((x + xa >= player2.getX() - Player.WIDTH)
            && ((y >= player2.getY())
                && (y <= (player2.getY() + Player.HEIGHT + (BALLSIZE - 1)))))
            xa = -SPEED;
        if (y + ya < topWall.getY() + Wall.WALLHEIGHT)
            ya = SPEED;
        if (y + ya > bottomWall.getY() - BALLSIZE)
            ya = -SPEED;

        x += xa;
        y += ya;
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

    /* Setters */
    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}