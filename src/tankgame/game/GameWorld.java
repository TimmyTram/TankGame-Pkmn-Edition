package tankgame.game;

import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.ResourceHandler;
import tankgame.display.Camera;
import tankgame.display.GameHUD;
import tankgame.display.Minimap;
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
    private Minimap minimap;
    private Camera camera1;
    private Camera camera2;
    private GameHUD gameHUD1;
    private GameHUD gameHUD2;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    private GameObjectCollections<Projectile> projectileGameObjectCollections;
    private GameObjectCollections<PowerUp> powerUpGameObjectCollections;
    private GameObjectCollections<Wall> wallGameObjectCollections;
    private GameObjectCollections<Tank> tankGameObjectCollections;

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
                this.tankGameObjectCollections.update();
                this.projectileGameObjectCollections.update();
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
//                if (this.tick >= 144 * 60) {
//                    this.lf.setFrame("end");
//                    return;
//                }


                if(tankGameObjectCollections.get(1).getIsLoser()) {
                    this.lf.setFrame("end");
                    System.out.println("TANK 1 WINS!");
                    return;
                } else if(tankGameObjectCollections.get(0).getIsLoser()) {
                    this.lf.setFrame("end");
                    System.out.println("TANK 2 WINS!");
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
//        this.t1.setX(300);
//        this.t1.setY(300);
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.tankGameObjectCollections = new GameObjectCollections<>();
        this.projectileGameObjectCollections = new GameObjectCollections<>();
        this.powerUpGameObjectCollections = new GameObjectCollections<>();
        this.wallGameObjectCollections = new GameObjectCollections<>();

        ResourceHandler.initImages();
        ResourceHandler.initSounds();
        GameMap.getInstance().initializeMap(this);
        BackgroundLoader.getInstance().initializeBackground();

        minimap = new Minimap();
        minimap.initializeMiniMapDimensions();
        camera1 = new Camera(tankGameObjectCollections.get(0), minimap.getScaledHeight());
        camera2 = new Camera(tankGameObjectCollections.get(1), minimap.getScaledHeight());

        double correctionOffset = 1.13;
        double leftRightOffset = 0.07;

        gameHUD1 = new GameHUD(
                tankGameObjectCollections.get(0),
                0,
                (int)(GameConstants.GAME_SCREEN_HEIGHT - minimap.getScaledHeight() * correctionOffset),
                (int)(minimap.getScaledWidth() * (correctionOffset + leftRightOffset)),
                (int)(GameConstants.GAME_SCREEN_HEIGHT - minimap.getScaledHeight() * correctionOffset)
        );
        gameHUD2 = new GameHUD(
                tankGameObjectCollections.get(1),
                GameConstants.GAME_SCREEN_WIDTH - (int)(minimap.getScaledWidth() * correctionOffset),
                (int)(GameConstants.GAME_SCREEN_HEIGHT - minimap.getScaledHeight() * correctionOffset),
                (GameConstants.GAME_SCREEN_WIDTH) - (GameConstants.GAME_SCREEN_WIDTH - (int)(minimap.getScaledWidth() * (correctionOffset - leftRightOffset) + 20)),
                (int)(GameConstants.GAME_SCREEN_HEIGHT - minimap.getScaledHeight() * correctionOffset)
        );

        TankController tc1 = new TankController(
                tankGameObjectCollections.get(0),
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE
        );
        TankController tc2 = new TankController(
                tankGameObjectCollections.get(1),
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

        g2.setColor(Color.black);
        g2.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);

        /*
            -------------- Draw gameObjects ----------------
         */

        this.tankGameObjectCollections.draw(buffer);
        this.projectileGameObjectCollections.draw(buffer);
        this.wallGameObjectCollections.draw(buffer);
        this.powerUpGameObjectCollections.draw(buffer);

        /*
            -------------- Draw Split Screen ----------------
         */

        camera1.drawSplitScreen(world);
        camera2.drawSplitScreen(world);
        BufferedImage leftScreen = camera1.getSplitScreen();
        BufferedImage rightScreen = camera2.getSplitScreen();
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, (GameConstants.GAME_SCREEN_WIDTH / 2) + 4, 0, null);

        /*
            -------------- Draw HUD ----------------
         */

        gameHUD1.drawHUD(g2);
        gameHUD2.drawHUD(g2);
        minimap.drawMinimap(world, g2);
    }

    public void addToWallObjectCollection(Wall wall) {
        this.wallGameObjectCollections.add(wall);
    }

    public void addToPowerUpGameObjectCollection(PowerUp powerUp) {
        this.powerUpGameObjectCollections.add(powerUp);
    }

    public void addToTankGameObjectCollection(Tank tank) {
        this.tankGameObjectCollections.add(tank);
    }

    public void addToProjectileGameObjectCollection(Projectile projectile) {
        this.projectileGameObjectCollections.add(projectile);
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

        for (int i = 0; i < this.tankGameObjectCollections.size(); i++) {
            Tank tank = this.tankGameObjectCollections.get(i);
            for (int j = 0; j < this.wallGameObjectCollections.size(); j++) {
                Wall wall = this.wallGameObjectCollections.get(j);
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
        for(int i = 0; i < this.projectileGameObjectCollections.size(); i++) {
            Projectile projectile = this.projectileGameObjectCollections.get(i);
            Rectangle projectileRectangle = projectile.getBounds();
            for(int j = 0; j < this.tankGameObjectCollections.size(); j++) {
                Tank tank = this.tankGameObjectCollections.get(j);
                if(projectileRectangle.intersects(tank.getBounds()) && projectile.getOwnership() != tank) {
                    System.out.println("Hit tank " + (this.tankGameObjectCollections.indexOf(tank) + 1) + "!");
                    if(this.projectileGameObjectCollections.remove(projectile)) {
                        ResourceHandler.getSound(GameConstants.RESOURCE_BULLET_SOUND_1_COLLIDE).play();
                        tank.takeDamage();
                        return;
                    }
                }
            }

            for(int j = 0; j < this.wallGameObjectCollections.size(); j++) {
                Wall wall = this.wallGameObjectCollections.get(j);
                Rectangle wallRectangle = wall.getBounds();
                if(projectileRectangle.intersects(wallRectangle)) {
                    wall.setDestroyed(true);
                    this.projectileGameObjectCollections.remove(projectile);
                    ResourceHandler.getSound(GameConstants.RESOURCE_BULLET_SOUND_1_COLLIDE).play();
                    if(wall.getDestroyed()) {
                        ResourceHandler.getSound(GameConstants.RESOURCE_ROCK_SMASH_SOUND).play();
                        this.wallGameObjectCollections.remove(wall);
                    }
                    return;
                }
            }
        }
    }
    private void collisionTankVsPowerUp() {
        for(int i = 0; i < this.tankGameObjectCollections.size(); i++) {
            Tank tank = this.tankGameObjectCollections.get(i);
            for(int j = 0; j < this.powerUpGameObjectCollections.size(); j++) {
                PowerUp powerUp = this.powerUpGameObjectCollections.get(j);
                Rectangle powerUpRectangle = powerUp.getBounds();
                if(tank.getBounds().intersects(powerUpRectangle)) {
                    powerUp.empower(tank);
                    powerUp.playSound();
                    this.powerUpGameObjectCollections.remove(powerUp);
                    return;
                }
            }
        }
    }
}