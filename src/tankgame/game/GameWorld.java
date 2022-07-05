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
    private BreakableWall breakableWall;

    private Heal heal;

    private Barrage barrage;

    private SpeedBoost speed;

    private final ProjectileHandler projectileHandler = new ProjectileHandler();
    private final PowerUpHandler powerUpHandler = new PowerUpHandler();

    private final WallHandler wallHandler = new WallHandler();
    private ArrayList<Tank> tanks = new ArrayList<>();

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
                this.projectileHandler.update();
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

        t1 = new Tank(300, 300, 0, 0, (short) 0, resourceHandler.getT1img(), projectileHandler);
        t2 = new Tank(600, 600, 0, 0, (short) 0, resourceHandler.getT2img(), projectileHandler);
        tanks.add(t1);
        tanks.add(t2);

        /*
        -------------------------------- TESTING OBJECTS --------------------------------
        */
        unbreakableWall = new UnbreakableWall(500, 500, resourceHandler.getUnbreakableWallImg());
        unbreakableWall2 = new UnbreakableWall(500, 548, resourceHandler.getUnbreakableWallImg());
        breakableWall = new BreakableWall(400, 400, resourceHandler.getBreakableWallImg());

        heal = new Heal(700, 700, resourceHandler.getHealImg());
        barrage = new Barrage(800, 800, resourceHandler.getBarrageImg());
        speed = new SpeedBoost(900, 800, resourceHandler.getSpeedImg());

        powerUpHandler.addPowerUps(heal);
        powerUpHandler.addPowerUps(barrage);
        powerUpHandler.addPowerUps(speed);

        wallHandler.addWall(unbreakableWall);
        wallHandler.addWall(unbreakableWall2);
        wallHandler.addWall(breakableWall);

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

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        this.projectileHandler.drawProjectiles(buffer);

        /*
        -------------------------------- TESTING OBJECTS --------------------------------
        */
//        this.breakableWall.drawImage(buffer);
//        this.unbreakableWall.drawImage(buffer);
//        this.unbreakableWall2.drawImage(buffer);

        this.wallHandler.drawWalls(buffer);
        this.powerUpHandler.drawPowerUps(buffer);
        /*
        -------------------------------- TESTING OBJECTS --------------------------------
        */

        g2.drawImage(world, 0, 0, null);
    }

    /*
        TODO:
        We could make this a collisionHandler class but unsure for now just want to get this working
        Need to handle tank1 vs bullet from tank2 [*]
        Need to handle tank2 vs bullet from tank1 [*]
        Need to handle wall vs any bullet [*]
        Need to handle any tank vs any wall [*]
        Need to handle any tank vs any powerup []
     */
    private void checkCollisions() {
        ArrayList<Wall> walls = this.wallHandler.getWalls();
        ArrayList<PowerUp> powerUps = this.powerUpHandler.getPowerUps();

        float offset = 1.1f; // offset is to push away tank from being inside of wall
        for(Tank tank : tanks) { // really buggy tank vs wall revisit later?
            //System.out.println("Tank Vx: " + tank.getVx() + " | Tank Vy: " + tank.getVy());
            for (Wall wall : walls) {
                Rectangle uwall = wall.getBounds();
                if(tank.getBoundsHorizontal().intersects(uwall)) {
                    if(tank.getVx() > 0) { // collision for left side of object
                        tank.setVx(0);
                        tank.setX(wall.getX() - (wall.getWidth() * offset));
                    }
                    if(tank.getVx() < 0) { // collision for right side of object
                        tank.setVx(0);
                        tank.setX(wall.getX() + (wall.getWidth() * offset));
                    }
                } else if(tank.getBoundsVertical().intersects(uwall)) {
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

        ArrayList<Projectile> projectiles = projectileHandler.getProjectiles();
        for(Projectile projectile : projectiles) {
            Rectangle r = projectile.getBounds();

            for(Tank tank : tanks) {
                if(r.intersects(tank.getBounds()) && projectile.getOwnership() != tank) {
                    System.out.println("Hit tank " + (tanks.indexOf(tank) + 1) + "!");
                    if(projectileHandler.destroyProjectile(projectile)) {
                        tank.takeDamage();
                        return;
                    }
                }
            }

            for(Wall wall : walls) {
                Rectangle uwall = wall.getBounds();
                if(r.intersects(uwall)) {
                    System.out.println("intersects with wall");
                    if(projectileHandler.destroyProjectile(projectile)) {
                        return;
                    }
                }
            }
        }

        for(Tank tank : tanks) {
            for(PowerUp powerUp : powerUps) {
                Rectangle pow = powerUp.getBounds();
                if(tank.getBounds().intersects(pow)) {
                    powerUp.empower(tank);
                    powerUpHandler.destroyPowerUp(powerUp);
                    return;
                }
            }
        }

    }

}
