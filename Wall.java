import java.awt.*;

public class Wall
{
    public static final int WALLHEIGHT = 10;

    private int x;
    private int y;
    private int wallLength;

    public Wall(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.wallLength = Game.FRAMEWIDTH;
    }

    public void paint(Graphics g)
    {
        Graphics2D page = (Graphics2D) g;
        page.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);

        page.fillRect(x, y, wallLength, WALLHEIGHT);
    }

    public int getY()
    {
        return y;
    }
}