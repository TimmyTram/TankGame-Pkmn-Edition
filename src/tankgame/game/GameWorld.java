package tankgame.game;

import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.ResourceHandler;
import tankgame.display.Camera;
import tankgame.game.powerups.PowerUp;
import tankgame.game.projectiles.Projectile;
import tankgame.game.tanks.Tank;
import tankgame.game.tanks.TankController;
import tankgame.game.walls.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameWorld extends JPanel implements Runnable {
    private BufferedImage world;
    private Camera camera1;
    private Camera camera2;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;

    private GameCollections<Projectile> projectileGameCollections;
    private GameCollections<PowerUp> powerUpGameCollections;
    private GameCollections<Wall> wallGameCollections;
    private GameCollections<Tank> tankGameCollections;

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
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.tankGameCollections = new GameCollections<>();
        this.projectileGameCollections = new GameCollections<>();
        this.powerUpGameCollections = new GameCollections<>();
        this.wallGameCollections = new GameCollections<>();

        ResourceHandler.initImages();
        ResourceHandler.initSounds();
        GameMap.getInstance().initializeMap(this);
        BackgroundLoader.getInstance().initializeBackground();

        t1 = new Tank(300, 300, 0, 0, (short) 0, ResourceHandler.getImage(GameConstants.RESOURCE_TANK_1), this.projectileGameCollections);
        t2 = new Tank(600, 600, 0, 0, (short) 0, ResourceHandler.getImage(GameConstants.RESOURCE_TANK_2), this.projectileGameCollections);
        this.tankGameCollections.add(t1);
        this.tankGameCollections.add(t2);
        camera1 = new Camera(t1);
        camera2 = new Camera(t2);

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
        BackgroundLoader.getInstance().drawImage(buffer);

        /*
            -------------- Draw gameObjects ----------------
         */

        this.tankGameCollections.draw(buffer);
        this.projectileGameCollections.draw(buffer);
        this.wallGameCollections.draw(buffer);
        this.powerUpGameCollections.draw(buffer);

        /*
            -------------- Draw Split Screen ----------------
         */
        camera1.drawSplitScreen(world);
        camera2.drawSplitScreen(world);
        BufferedImage leftScreen = camera1.getSplitScreen();
        BufferedImage rightScreen = camera2.getSplitScreen();
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, (GameConstants.GAME_SCREEN_WIDTH / 2) + 1, 0, null);


        /*
            -------------- Draw Minimap ----------------
         */
        BufferedImage minimap = world.getSubimage(0, 0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        g2.scale(0.2, 0.2);
        g2.drawImage(minimap, GameConstants.GAME_SCREEN_WIDTH * 2 - GameConstants.GAME_SCREEN_WIDTH / 8, GameConstants.GAME_SCREEN_HEIGHT * 3 + GameConstants.GAME_SCREEN_HEIGHT / 4, null);
    }

    public void addToWallCollection(Wall wall) {
        this.wallGameCollections.add(wall);
    }

    public void addToPowerUpGameCollection(PowerUp powerUp) {
        this.powerUpGameCollections.add(powerUp);
    }

    /*
            THIS IS JUST A PLACEHOLDER FOR COLLISION FUNCTIONS | FINAL PLAN IS TO ACTUALLY PUT THESE COLLISION METHODS SOMEWHERE ELSE
            MAKE SURE TO CHANGE THE REMOVE TO NOT ACTUALLY REMOVE IN THE LOOP ; CURRENT SOLUTION DOESN'T SEEM THAT GOOD
            SHOULD MARK OBJECTS FOR DEATH AND THEN REMOVE AFTER THE LOOP removeIf() <-- Look at this
     */
    private void checkCollisions() {
        collisionWallVsTank();
        collisionProjectile();
        collisionTankVsPowerUp();
    }

    private void collisionWallVsTank() {
        float offset = 1.09f;

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
                    // if diagonal based collision we want to prioritize the horizontal one for now.
                } else if (tank.getBoundsVertical().intersects(wallRectangle) && !tank.getBoundsHorizontal().intersects(wallRectangle)) {
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
                        ResourceHandler.getSound(GameConstants.RESOURCE_BULLET_SOUND_1_COLLIDE).play();
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
                    ResourceHandler.getSound(GameConstants.RESOURCE_BULLET_SOUND_1_COLLIDE).play();
                    if(wall.getDestroyed()) {
                        ResourceHandler.getSound(GameConstants.RESOURCE_ROCK_SMASH_SOUND).play();
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
                    powerUp.playSound();
                    this.powerUpGameCollections.remove(powerUp);
                    return;
                }
            }
        }
    }
}