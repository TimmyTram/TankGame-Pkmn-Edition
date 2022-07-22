package tankgame.game;

import tankgame.GameConstants;
import tankgame.Launcher;
import tankgame.ResourceHandler;
import tankgame.display.Camera;
import tankgame.display.GameHUD;
import tankgame.display.Minimap;
import tankgame.game.projectiles.Projectile;
import tankgame.game.tanks.Tank;
import tankgame.game.tanks.TankController;

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
    private Launcher lf;
    private long tick = 0;
    private GameObjectCollections<MovableObject> movableObjectGameObjectCollections;
    private GameObjectCollections<StationaryObject> stationaryObjectGameObjectCollections;

    private boolean gameOver = false;

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
            if(gameOver) {
                this.resetGame();
            }
            while (true) {
                this.tick++;
                this.movableObjectGameObjectCollections.update();
                checkCollisions();
                deleteGarbage();
                this.repaint();   // redraw game

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                 */
                Thread.sleep(1000 / 144);

                if( ((Tank)(this.movableObjectGameObjectCollections.get(1))).getIsLoser() ) {
                    this.lf.setFrame("end");
                    System.out.println("TANK 1 WINS!");
                    gameOver = true;
                    return;
                } else if( ((Tank)(this.movableObjectGameObjectCollections.get(0))).getIsLoser() ) {
                    this.lf.setFrame("end");
                    System.out.println("TANK 2 WINS!");
                    gameOver = true;
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
        this.InitializeGame();
        this.gameOver = false;
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.movableObjectGameObjectCollections = new GameObjectCollections<>();
        this.stationaryObjectGameObjectCollections = new GameObjectCollections<>();

        ResourceHandler.initImages();
        ResourceHandler.initSounds();
        GameMap.getInstance().initializeMap(this);
        BackgroundLoader.getInstance().initializeBackground();

        minimap = new Minimap();
        minimap.initializeMiniMapDimensions();
        camera1 = new Camera((Tank) movableObjectGameObjectCollections.get(0), minimap.getScaledHeight());
        camera2 = new Camera((Tank) movableObjectGameObjectCollections.get(1), minimap.getScaledHeight());

        double correctionOffset = 1.13;
        double leftRightOffset = 0.07;

        gameHUD1 = new GameHUD(
                (Tank) movableObjectGameObjectCollections.get(0),
                0,
                (int)(GameConstants.GAME_SCREEN_HEIGHT - minimap.getScaledHeight() * correctionOffset),
                (int)(minimap.getScaledWidth() * (correctionOffset + leftRightOffset))
        );
        gameHUD2 = new GameHUD(
                (Tank) movableObjectGameObjectCollections.get(1),
                GameConstants.GAME_SCREEN_WIDTH - (int)(minimap.getScaledWidth() * correctionOffset),
                (int)(GameConstants.GAME_SCREEN_HEIGHT - minimap.getScaledHeight() * correctionOffset),
                (GameConstants.GAME_SCREEN_WIDTH) - (GameConstants.GAME_SCREEN_WIDTH - (int)(minimap.getScaledWidth() * (correctionOffset - leftRightOffset) + 20))
        );

        TankController tc1 = new TankController(
                (Tank) movableObjectGameObjectCollections.get(0),
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE
        );
        TankController tc2 = new TankController(
                (Tank) movableObjectGameObjectCollections.get(1),
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

        this.movableObjectGameObjectCollections.draw(buffer);
        this.stationaryObjectGameObjectCollections.draw(buffer);

        camera1.drawSplitScreen(world);
        camera2.drawSplitScreen(world);
        BufferedImage leftScreen = camera1.getSplitScreen();
        BufferedImage rightScreen = camera2.getSplitScreen();
        g2.drawImage(leftScreen, 0, 0, null);
        g2.drawImage(rightScreen, (GameConstants.GAME_SCREEN_WIDTH / 2) + 4, 0, null);

        gameHUD1.drawHUD(g2);
        gameHUD2.drawHUD(g2);
        minimap.drawMinimap(world, g2);
    }

    public void addToMovableGameObjectCollections(MovableObject movableObject) {
        this.movableObjectGameObjectCollections.add(movableObject);
    }

    public void addToStationaryGameObjectCollections(StationaryObject stationaryObject) {
        this.stationaryObjectGameObjectCollections.add(stationaryObject);
    }

    private void checkCollisions() {
        for(int i = 0; i < this.movableObjectGameObjectCollections.size(); i++) {
            MovableObject movableObject = this.movableObjectGameObjectCollections.get(i);
            for(int j = 0; j < this.stationaryObjectGameObjectCollections.size(); j++) {
                StationaryObject stationaryObject = this.stationaryObjectGameObjectCollections.get(j);
                if(movableObject.getHitBox().intersects(stationaryObject.getHitBox())) {
                    movableObject.handleCollision(stationaryObject);
                }
            }
        }
    }

    private void deleteGarbage() {
        for(int i = 0; i < this.movableObjectGameObjectCollections.size(); i++) {
            MovableObject movableObject = this.movableObjectGameObjectCollections.get(i);
            if(movableObject instanceof Projectile && ((Projectile) movableObject).getIsDestroyed()) {
                this.movableObjectGameObjectCollections.remove(movableObject);
            }
        }

        for(int i = 0; i < this.stationaryObjectGameObjectCollections.size(); i++) {
            StationaryObject stationaryObject = this.stationaryObjectGameObjectCollections.get(i);
            if(stationaryObject.getIsDestroyed()) {
                this.stationaryObjectGameObjectCollections.remove(stationaryObject);
            }
        }
    }

}