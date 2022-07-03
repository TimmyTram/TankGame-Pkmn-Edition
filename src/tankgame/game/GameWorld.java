package tankgame.game;

import tankgame.GameConstants;
import tankgame.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameWorld extends JPanel implements Runnable {
    private BufferedImage world;

    private Tank t1;

    private Tank t2; // testing 2nd tank

    private Bullet bullet; // testing bullet

    private Launcher lf;
    private long tick = 0;

    /**
     *
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
    }

    @Override
    public void run() {
        try {
            this.resetGame();
            while (true) {
                this.tick++;
                this.t1.update(); // update tank

                this.t2.update(); // testing 2nd tank
                this.bullet.update(); // testing bullet

                this.repaint();   // redraw game

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                 */
                Thread.sleep(1000 / 144);

                /*
                 * simulate an end game event
                 * we will do this with by ending the game when ~8 seconds has passed.
                 * This will need to be changed since the will always close after 8 seconds
                 */
                if (this.tick >= 144 * 8) {
                    this.lf.setFrame("end");
                    return;
                }

            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame() {
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        BufferedImage bulletImg = null; // testing bullet
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory. When running a jar, class loaders will read from withing the jar.
             */
            t1img = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("tank1.png"),
                            "Could not find tank1.png")
            );

            t2img = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("tank1.png"),
                            "Could not find tank1.png")
            );

            bulletImg = ImageIO.read(
                    Objects.requireNonNull(GameWorld.class.getClassLoader().getResource("bullet.gif"),
                            "Could not find bullet.gif")
            );

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(300, 300, 0, 0, (short) 0, t1img);
        t2 = new Tank(600, 600, 0, 0, (short) 0, t2img);
        bullet = new Bullet(500, 500, 1, 1, (short) 0, GameConstants.BULLET_SPEED, bulletImg); // testing bullet
        TankController tc1 = new TankController(
                t1,
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE
        );
        TankController tc2 = new TankController(
                t2,
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER
        );
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        this.bullet.drawImage(buffer); // testing bullet
        g2.drawImage(world, 0, 0, null);
    }
}
