package tankgame.game;

import tankgame.game.powerups.PowerUp;
import tankgame.game.walls.Wall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class GameMap {

    private static GameMap instance;

    private GameMap() {}

    public static GameMap getInstance() {
        if(instance == null)
            instance = new GameMap();
        return instance;
    }

    public void initializeMap(GameWorld gw) {
        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(GameWorld.class.getClassLoader().getResourceAsStream("maps/map2.txt"))))) {
            GameObjectFactory gameObjectFactory = new GameObjectFactory();
            for(int col = 0; mapReader.ready(); col++) {
                String[] items = mapReader.readLine().split("");
                for(int row = 0; row < items.length; row++) {
                    GameObject gameObject = gameObjectFactory.createGameObject(items[row], row, col);


                    // not the best solution ; I could make one singular collection with my GameCollections class
                    // e.g: GameCollection<GameObject> | <-- this would mean I have to change my collision detection which
                    // I already plan on doing. should ultimately have the following code:
                    // gw.addToGameCollection(gameObject);
                    if(gameObject instanceof Wall) {
                        gw.addToWallCollection((Wall) gameObject);
                    } else if(gameObject instanceof PowerUp) {
                        gw.addToPowerUpGameCollection((PowerUp) gameObject);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.exit(-2);
        }
    }
}
