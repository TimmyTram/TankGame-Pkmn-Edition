package tankgame.game;

import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.ResourceHandler;
import tankgame.game.powerups.Barrage;
import tankgame.game.powerups.PowerUp;
import tankgame.game.powerups.SpeedBoost;
import tankgame.game.powerups.Heal;
import tankgame.game.walls.BreakableWall;
import tankgame.game.walls.UnbreakableWall;
import tankgame.game.walls.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameWorld extends JPanel implements Runnable {
    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;

    /*
        -------------------------------- TESTING OBJECTS --------------------------------
     */
    private UnbreakableWall unbreakableWall;

    private UnbreakableWall unbreakableWall2;

    private UnbreakableWall unbreakableWall3;
    private BreakableWall breakableWall;

    private BreakableWall breakableWall2;

    private Heal heal;

    private Barrage barrage;

    private SpeedBoost speed;

    private GameCollections<Projectile> projectileGameCollections;
    private GameCollections<PowerUp> powerUpGameCollections;
    private GameCollections<Wall> wallGameCollections;
    private GameCollections<Tank> tankGameCollections;


    /*
        -------------------------------- TESTING OBJECTS --------------------------------
     */

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
                this.t1.update();
                this.t2.update();
                this.projectileGameCollections.update();
                checkCollisions();
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
                if (this.tick >= 144 * 60) {
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

        this.tankGameCollections = new GameCollections<>();
        this.projectileGameCollections = new GameCollections<>();
        this.powerUpGameCollections = new GameCollections<>();
        this.wallGameCollections = new GameCollections<>();

        ResourceHandler resourceHandler = ResourceHandler.getInstance();
        resourceHandler.initializeResources(
                "tank1.png",
                "dawn.png",
                "pokeball.png",
                GameConstants.RESOURCE_BREAKABLE_WALL,
                GameConstants.RESOURCE_UNBREAKABLE_WALL,
                GameConstants.RESOURCE_HEAL,
                GameConstants.RESOURCE_BARRAGE,
                GameConstants.RESOURCE_SPEED
        );

        t1 = new Tank(300, 300, 0, 0, (short) 0, resourceHandler.getT1img(), this.projectileGameCollections);
        t2 = new Tank(600, 600, 0, 0, (short) 0, resourceHandler.getT2img(), this.projectileGameCollections);
        this.tankGameCollections.add(t1);
        this.tankGameCollections.add(t2);

        /*
        -------------------------------- TESTING OBJECTS --------------------------------
        */
        unbreakableWall = new UnbreakableWall(500, 500, resourceHandler.getUnbreakableWallImg());
        unbreakableWall2 = new UnbreakableWall(500, 548, resourceHandler.getUnbreakableWallImg());
        unbreakableWall3 = new UnbreakableWall(548, 548, resourceHandler.getUnbreakableWallImg());
        breakableWall2 = new BreakableWall(596, 548, resourceHandler.getBreakableWallImg());

        breakableWall = new BreakableWall(400, 400, resourceHandler.getBreakableWallImg());

        heal = new Heal(700, 700, resourceHandler.getHealImg());
        barrage = new Barrage(800, 800, resourceHandler.getBarrageImg());
        speed = new SpeedBoost(900, 800, resourceHandler.getSpeedImg());


        this.powerUpGameCollections.add(heal);
        this.powerUpGameCollections.add(barrage);
        this.powerUpGameCollections.add(speed);

        this.wallGameCollections.add(unbreakableWall);
        this.wallGameCollections.add(unbreakableWall2);
        this.wallGameCollections.add(unbreakableWall3);
        this.wallGameCollections.add(breakableWall);
        this.wallGameCollections.add(breakableWall2);

        /*
        -------------------------------- TESTING OBJECTS --------------------------------
        */

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

        /*
        -------------------------------- TEMPORARY CODE --------------------------------
        */
        // this is just so we can see things properly.
        buffer.setColor(Color.black);
        buffer.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);
        buffer.setColor(Color.white);
        /*
        -------------------------------- TEMPORARY CODE --------------------------------
        */
        this.tankGameCollections.draw(buffer);
        /*
        -------------------------------- TESTING OBJECTS --------------------------------
        */

        this.projectileGameCollections.draw(buffer);
        this.wallGameCollections.draw(buffer);
        this.powerUpGameCollections.draw(buffer);

        /*
        -------------------------------- TESTING OBJECTS --------------------------------
        */

        g2.drawImage(world, 0, 0, null);
    }

    private void checkCollisions() {
        collisionWallVsTank();
        collisionProjectile();
        collisionTankVsPowerUp();
    }

    private void collisionWallVsTank() {
        float offset = 1.2f;

        for (int i = 0; i < this.tankGameCollections.size(); i++) {
            Tank tank = this.tankGameCollections.get(i);
            for (int j = 0; j < this.wallGameCollections.size(); j++) {
                Wall wall = this.wallGameCollections.get(j);
                Rectangle wallRectangle = wall.getBounds();
                if (tank.getBoundsHorizontal().intersects(wallRectangle)) {
                    if (tank.getVx() > 0) { // collision for left side of object
                        tank.setVx(0);
                        tank.setX(wall.getX() - (wall.getWidth() * offset));
                    }
                    if (tank.getVx() < 0) { // collision for right side of object
                        tank.setVx(0);
                        tank.setX(wall.getX() + (wall.getWidth() * offset));
                    }
                } else if (tank.getBoundsVertical().intersects(wallRectangle)) {
                    if (tank.getVy() > 0) { // collision for top side of object
                        tank.setVy(0);
                        tank.setY(wall.getY() - (wall.getHeight() * offset));
                    }
                    if (tank.getVy() < 0) { // collision for bottom side of object
                        tank.setVy(0);
                        tank.setY(wall.getY() + wall.getHeight() * offset);
                    }
                }
            }
        }
    }

    private void collisionProjectile() {
        for(int i = 0; i < this.projectileGameCollections.size(); i++) {
            Projectile projectile = this.projectileGameCollections.get(i);
            Rectangle projectileRectangle = projectile.getBounds();
            for(int j = 0; j < this.tankGameCollections.size(); j++) {
                Tank tank = this.tankGameCollections.get(j);
                if(projectileRectangle.intersects(tank.getBounds()) && projectile.getOwnership() != tank) {
                    System.out.println("Hit tank " + (this.tankGameCollections.indexOf(tank) + 1) + "!");
                    if(this.projectileGameCollections.remove(projectile)) {
                        tank.takeDamage();
                        return;
                    }
                }
            }

            for(int j = 0; j < this.wallGameCollections.size(); j++) {
                Wall wall = this.wallGameCollections.get(j);
                Rectangle wallRectangle = wall.getBounds();
                if(projectileRectangle.intersects(wallRectangle)) {
                    wall.setDestroyed(true);
                    this.projectileGameCollections.remove(projectile);
                    if(wall.getDestroyed()) {
                        this.wallGameCollections.remove(wall);
                    }
                    return;
                }
            }
        }
    }
    private void collisionTankVsPowerUp() {
        for(int i = 0; i < this.tankGameCollections.size(); i++) {
            Tank tank = this.tankGameCollections.get(i);
            for(int j = 0; j < this.powerUpGameCollections.size(); j++) {
                PowerUp powerUp = this.powerUpGameCollections.get(j);
                Rectangle powerUpRectangle = powerUp.getBounds();
                if(tank.getBounds().intersects(powerUpRectangle)) {
                    powerUp.empower(tank);
                    this.powerUpGameCollections.remove(powerUp);
                    return;
                }
            }
        }
    }
}