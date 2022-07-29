package tankgame.game;

import tankgame.Sound;
import tankgame.constants.GameConstants;
import tankgame.Launcher;
import tankgame.ResourceHandler;
import tankgame.constants.ResourceConstants;
import tankgame.display.Camera;
import tankgame.display.GameHUD;
import tankgame.display.Minimap;
import tankgame.game.moveableObjects.MoveableObject;
import tankgame.game.moveableObjects.projectiles.Projectile;
import tankgame.game.stationaryObjects.StationaryObject;
import tankgame.game.moveableObjects.tanks.Tank;
import tankgame.game.moveableObjects.tanks.TankController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GameWorld extends JPanel implements Runnable {
    private BufferedImage world;
    private Minimap minimap;
    private Camera camera1;
    private Camera camera2;
    private GameHUD gameHUD1;
    private GameHUD gameHUD2;
    private Tank t1;
    private Tank t2;
    private TankController tc1;
    private TankController tc2;
    private Launcher lf;
    private long tick = 0;
    private GameObjectCollections<MoveableObject> moveableObjectGameObjectCollections;
    private GameObjectCollections<StationaryObject> stationaryObjectGameObjectCollections;
    private GameObjectCollections<GameObject> collisionlessGameObjectCollections;
    private ArrayList<int[]> emptySpaces;
    private GameState.RunningState runningState;
    private String gameMap;

    /**
     *
     * @param lf
     */
    public GameWorld(Launcher lf) {
        this.lf = lf;
        System.out.println("Initializing Resources . . .");
        ResourceHandler.initImages();
        ResourceHandler.initSounds();
        ResourceHandler.initAnimations();
        ResourceHandler.initMaps();
    }

    @Override
    public void run() {
        try {
            if(!this.runningState.getState()) {
                System.out.println("Resetting Game . . .");
                this.resetGame();
            }
            //Sound music = new Sound(ResourceHandler.getSound(ResourceConstants.SOUND_MUSIC_DRIFTVEIL_CITY));
            //Thread musicThread = new Thread(music);
            //musicThread.start();
            while (true) {
                this.tick++;
                this.moveableObjectGameObjectCollections.update();
                checkCollisions();
                deleteGarbage();
                this.repaint();   // redraw game

                /*
                 * Sleep for 1000/144 ms (~6.9ms). This is done to have our
                 * loop run at a fixed rate per/sec.
                 */
                Thread.sleep(1000 / 144);

                if(this.t2.getIsLoser()) {
                    System.out.println("TANK 1 WINS!");
                    GameState.PLAYER_WINNER = 1;
                    this.runningState = this.runningState.nextState();
                    //music.stopSound();
                    //musicThread.interrupt();
                    this.lf.setFrame("end");
                    return;
                } else if(this.t1.getIsLoser()) {
                    System.out.println("TANK 2 WINS!");
                    GameState.PLAYER_WINNER = 2;
                    this.runningState = this.runningState.nextState();
                    //music.stopSound();
                    //musicThread.interrupt();
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
        this.runningState = GameState.RunningState.RUNNING;
        this.moveableObjectGameObjectCollections.clear();
        this.stationaryObjectGameObjectCollections.clear();
        this.collisionlessGameObjectCollections.clear();
        this.emptySpaces.clear();
        this.loadMap();
        this.emptySpaces = GameMap.getInstance().getEmptySpaces();
        BackgroundLoader.getInstance().initializeBackground();
        this.initTanks();
        this.t1.setValidSpawnLocations(this.emptySpaces);
        this.t2.setValidSpawnLocations(this.emptySpaces);
        this.initHUD();
        this.initControllers();
    }

    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void InitializeGame() {
        System.out.println("Initializing Game . . .");
        this.runningState = GameState.RunningState.RUNNING;
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                GameConstants.WORLD_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        this.moveableObjectGameObjectCollections = new GameObjectCollections<>();
        this.stationaryObjectGameObjectCollections = new GameObjectCollections<>();
        this.collisionlessGameObjectCollections = new GameObjectCollections<>();
        this.emptySpaces = new ArrayList<>();
        this.loadMap();
        this.emptySpaces = GameMap.getInstance().getEmptySpaces();
        BackgroundLoader.getInstance().initializeBackground();
        this.initTanks();
        this.t1.setValidSpawnLocations(this.emptySpaces);
        this.t2.setValidSpawnLocations(this.emptySpaces);
        this.initHUD();
        this.initControllers();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        BackgroundLoader.getInstance().drawImage(buffer);

        g2.setColor(Color.black);
        g2.fillRect(0, 0, GameConstants.GAME_SCREEN_WIDTH, GameConstants.GAME_SCREEN_HEIGHT);

        this.moveableObjectGameObjectCollections.draw(buffer);
        this.stationaryObjectGameObjectCollections.draw(buffer);
        this.collisionlessGameObjectCollections.draw(buffer);

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

    public void addToMovableGameObjectCollections(MoveableObject moveableObject) {
        this.moveableObjectGameObjectCollections.add(moveableObject);
    }

    public void addToStationaryGameObjectCollections(StationaryObject stationaryObject) {
        this.stationaryObjectGameObjectCollections.add(stationaryObject);
    }

    public void addToCollisionlessGameObjectCollections(GameObject gameObject) {
        this.collisionlessGameObjectCollections.add(gameObject);
    }

    public void selectMap(String map) {
        this.gameMap = map;
        this.resetGame();
    }

    private void checkCollisions() {
        for(int i = 0; i < this.moveableObjectGameObjectCollections.size(); i++) {
            MoveableObject moveableObject = this.moveableObjectGameObjectCollections.get(i);
            for(int j = 0; j < this.stationaryObjectGameObjectCollections.size(); j++) {
                StationaryObject stationaryObject = this.stationaryObjectGameObjectCollections.get(j);
                if(moveableObject.getHitBox().intersects(stationaryObject.getHitBox())) {
                    moveableObject.handleCollision(stationaryObject);
                }
            }
            for(int k = 0; k < this.moveableObjectGameObjectCollections.size(); k++) {
                MoveableObject otherMoveableObject = this.moveableObjectGameObjectCollections.get(k);
                if(moveableObject.getHitBox().intersects(otherMoveableObject.getHitBox())) {
                    moveableObject.handleCollision(otherMoveableObject);
                }
            }
        }
    }

    private void deleteGarbage() {
        for(int i = 0; i < this.moveableObjectGameObjectCollections.size(); i++) {
            MoveableObject moveableObject = this.moveableObjectGameObjectCollections.get(i);
            if(moveableObject instanceof Projectile && ((Projectile) moveableObject).getIsDestroyed()) {
                this.moveableObjectGameObjectCollections.remove(moveableObject);
            }
        }

        for(int i = 0; i < this.stationaryObjectGameObjectCollections.size(); i++) {
            StationaryObject stationaryObject = this.stationaryObjectGameObjectCollections.get(i);
            if(stationaryObject.getIsDestroyed()) {
                this.stationaryObjectGameObjectCollections.remove(stationaryObject);
            }
        }
    }

    private void loadMap() {
        if(this.gameMap == null) {
            int maxChoices = ResourceHandler.getNumberOfMaps();
            int randChoice = (new Random()).nextInt(maxChoices);
            GameMap.getInstance().initializeMap(this, ResourceHandler.getGameMap(randChoice));
            System.out.println("Loading into " + ResourceHandler.getGameMap(randChoice) + " . . .");
        } else {
            System.out.println("Loading into " + this.gameMap + " . . .");
            GameMap.getInstance().initializeMap(this, ResourceHandler.getGameMap(this.gameMap));
        }
    }

    private void initTanks() {
        if(((Tank) moveableObjectGameObjectCollections.get(0)).getPlayerID() == 1) {
            this.t1 = (Tank) moveableObjectGameObjectCollections.get(0);
            this.t2 = (Tank) moveableObjectGameObjectCollections.get(1);
        } else {
            this.t1 = (Tank) moveableObjectGameObjectCollections.get(1);
            this.t2 = (Tank) moveableObjectGameObjectCollections.get(0);
        }
    }

    private void initHUD() {
        this.minimap = new Minimap();
        this.camera1 = new Camera(t1, (minimap.getScaledHeight() / 2) + 12);
        this.camera2 = new Camera(t2, (minimap.getScaledHeight() / 2) + 12);

        this.gameHUD1 = new GameHUD(
                this.t1,
                0,
                (GameConstants.GAME_SCREEN_HEIGHT - ((this.minimap.getScaledHeight() / 2 + 12))),
                this.minimap.getScaledWidth(),
                ResourceHandler.getImage(ResourceConstants.IMAGES_HUD_1)
        );

        this.gameHUD2 = new GameHUD(
                this.t2,
                (GameConstants.GAME_SCREEN_WIDTH - this.minimap.getScaledWidth()),
                (GameConstants.GAME_SCREEN_HEIGHT - ((this.minimap.getScaledHeight() / 2 + 12))),
                this.minimap.getScaledWidth(),
                ResourceHandler.getImage(ResourceConstants.IMAGES_HUD_2)
        );
    }

    private void initControllers() {
        this.tc1 = new TankController(
                this.t1,
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_SPACE
        );
        this.tc2 = new TankController(
                this.t2,
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_ENTER
        );
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }

}