package tankgame.game;

import tankgame.game.moveableObjects.MoveableObject;
import tankgame.game.stationaryObjects.StationaryObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class GameMap {

    private static GameMap instance;
    private static final ArrayList<int[]> emptySpaces = new ArrayList<>();

    private GameMap() {}

    public static GameMap getInstance() {
        if(instance == null)
            instance = new GameMap();
        return instance;
    }

    public ArrayList<int[]> getEmptySpaces() {
        return emptySpaces;
    }

    public void initializeMap(GameWorld gw) {
        try(BufferedReader mapReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(GameWorld.class.getClassLoader().getResourceAsStream("maps/map.csv"))))) {
            GameObjectFactory gameObjectFactory = new GameObjectFactory();
            for(int col = 0; mapReader.ready(); col++) {
                String[] items = mapReader.readLine().split(",");
                for(int row = 0; row < items.length; row++) {
                    String gameObjectID = items[row].replaceAll("\\s+", "");
                    gameObjectID = gameObjectID.toUpperCase();
                    GameObject gameObject = gameObjectFactory.createGameObject(gameObjectID, row, col, gw);

                    if(gameObject instanceof StationaryObject)
                        gw.addToStationaryGameObjectCollections((StationaryObject) gameObject);
                    else if(gameObject instanceof MoveableObject)
                        gw.addToMovableGameObjectCollections((MoveableObject) gameObject);
                    else if(gameObject == null) {
                        emptySpaces.add(new int[]{col, row});
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.exit(-2);
        }
    }
}
