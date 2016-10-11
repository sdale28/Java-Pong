/* Simple Pong game. Use W and S to control the left player and the up and down
   arrow keys to control the right player. If you want the left player to become
   controlled by AI, hit T. If you want the right player to be controlled by AI,
   hit G. Hit these buttons, respectively to give control back to the player. */

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;

public class Game extends JPanel
{
    public static final int FRAMEWIDTH = 1200;
    public static final int FRAMEHEIGHT = 800;
    public static final int SCOREVAL = 1;
    public static final int SPEED = 5;

    private static final int PLAYERBUFFER = 20;
    private static final int WALLBUFFER = 20;
    private static final int CENTERTHICK = 6;
    private static Wall topWall;
    private static Wall bottomWall;
    private static Ball ball;
    private static Player player1;
    private static Player player2;
    private static boolean tHit = false;
    private static boolean gHit = false;

    public Game()
    {
        addKeyListener(new KeyListener()
        {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e)
            {
                player1.keyReleased(e);
                player2.keyReleased(e);
                if (!tHit && (e.getKeyCode() == KeyEvent.VK_T))
                {
                    player1.setPlayerType(Player.AI);
                    tHit = true;
                }
                else if (tHit && e.getKeyCode() == KeyEvent.VK_T)
                {
                    player1.setPlayerType(Player.LEFTPLAYER);
                    tHit = false;
                }
                if (!gHit && (e.getKeyCode() == KeyEvent.VK_G))
                {
                    player2.setPlayerType(Player.AI);
                    gHit = true;
                }
                else if (gHit && (e.getKeyCode() == KeyEvent.VK_G))
                {
                    player2.setPlayerType(Player.RIGHTPLAYER);
                    gHit = false;
                }
            }
            public void keyPressed(KeyEvent e)
            {
                player1.keyPressed(e);
                player2.keyPressed(e);
            }
        });
        setFocusable(true);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D page = (Graphics2D) g;
        page.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                              RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background with black:
        page.setColor(Color.BLACK);
        page.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);

        // Paint the center line:
        page.setColor(Color.WHITE);
        int diff = FRAMEHEIGHT - (2 * WALLBUFFER) - (2 * Wall.WALLHEIGHT);
        int numLines = 20; // even number
        int y = WALLBUFFER + Wall.WALLHEIGHT;
        int lineLength = diff / (numLines + numLines - 1) + 1;
        for (int i = 0; i < 2 * numLines - 1; i++)
        {
            if (i % 2 == 0)
                page.fillRect((FRAMEWIDTH / 2) - (CENTERTHICK / 2), y,
                              CENTERTHICK, lineLength);
            y += lineLength;
        }

        // Paint the score:
        page.setColor(Color.WHITE);
        Font font=new Font(null, Font.PLAIN, 80);
        page.setFont(font);
        String leftScoreString = "" + player1.getScore();
        String rightScoreString = "" + player2.getScore();
        page.drawString(leftScoreString, (FRAMEWIDTH / 2) - 100, 110);
        page.drawString(rightScoreString, (FRAMEWIDTH / 2) + 50, 110);

        // paint sprites:
        page.setColor(Color.WHITE);
        topWall.paint(page);
        bottomWall.paint(page);
        ball.paint(page);
        player1.paint(page);
        player2.paint(page);
    }

    public static void main(String args[]) throws InterruptedException
    {
        JFrame frame = new JFrame("Pong");
        Game game = new Game();
        frame.add(game);
        // Set JFrame size to incorporate window boundaries:
        frame.getContentPane().setPreferredSize(new Dimension(FRAMEWIDTH,
                                                FRAMEHEIGHT));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        // sprites:
        topWall = new Wall(0, WALLBUFFER);
        bottomWall = new Wall(0, FRAMEHEIGHT - Wall.WALLHEIGHT - WALLBUFFER);
        player1 = new Player(PLAYERBUFFER, (FRAMEHEIGHT / 2) - Player.HEIGHT,
                             topWall, bottomWall, Player.LEFTPLAYER);
        player2 = new Player(FRAMEWIDTH - Player.WIDTH - PLAYERBUFFER,
                             (FRAMEHEIGHT / 2) - Player.HEIGHT,
                             topWall, bottomWall, Player.RIGHTPLAYER);
        ball = new Ball((FRAMEWIDTH / 2) - (Ball.BALLSIZE / 2),
                        (FRAMEHEIGHT / 2) - (Ball.BALLSIZE / 2), topWall,
                        bottomWall, player1, player2);
        player1.setBall(ball);
        player2.setBall(ball);

        while (true)
        {
            player1.movePlayer();
            player2.movePlayer();
            ball.moveBall();
            game.repaint();
            Thread.sleep(SPEED);
        }
    }
}