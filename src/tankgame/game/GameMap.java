package tankgame.game;

import tankgame.GameConstants;
import tankgame.ResourceHandler;
import tankgame.game.powerups.Barrage;
import tankgame.game.powerups.Heal;
import tankgame.game.powerups.PowerUp;
import tankgame.game.powerups.SpeedBoost;
import tankgame.game.walls.BreakableWall;
import tankgame.game.walls.UnbreakableWall;
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
            String[] size = mapReader.readLine().split(",");
            for(int col = 0; mapReader.ready(); col++) {
                String[] items = mapReader.readLine().split("");
                for(int row = 0; row < items.length; row++) {
                    switch(items[row]) {
                        case "3", "9" -> {
                            Wall wall = new UnbreakableWall(
                                    row * ResourceHandler.getImage(GameConstants.RESOURCE_UNBREAKABLE_WALL).getWidth(),
                                    col * ResourceHandler.getImage(GameConstants.RESOURCE_UNBREAKABLE_WALL).getHeight(),
                                    ResourceHandler.getImage(GameConstants.RESOURCE_UNBREAKABLE_WALL)
                            );
                            gw.addToWallCollection(wall);
                        }
                        case "2" -> {
                            Wall wall = new BreakableWall(
                                    row * ResourceHandler.getImage(GameConstants.RESOURCE_BREAKABLE_WALL).getWidth(),
                                    col * ResourceHandler.getImage(GameConstants.RESOURCE_BREAKABLE_WALL).getHeight(),
                                    ResourceHandler.getImage(GameConstants.RESOURCE_BREAKABLE_WALL)
                            );
                            gw.addToWallCollection(wall);
                        }

                        case "4" -> {
                            PowerUp powerUp = new Heal(
                                    row * ResourceHandler.getImage(GameConstants.RESOURCE_HEAL).getWidth(),
                                    col * ResourceHandler.getImage(GameConstants.RESOURCE_HEAL).getHeight(),
                                    ResourceHandler.getImage(GameConstants.RESOURCE_HEAL)
                            );
                            gw.addToPowerUpGameCollection(powerUp);
                        }

                        case "5" -> {
                            PowerUp powerUp = new Barrage(
                                    row * ResourceHandler.getImage(GameConstants.RESOURCE_BARRAGE).getWidth(),
                                    col * ResourceHandler.getImage(GameConstants.RESOURCE_BARRAGE).getHeight(),
                                    ResourceHandler.getImage(GameConstants.RESOURCE_BARRAGE)
                            );
                            gw.addToPowerUpGameCollection(powerUp);
                        }

                        case "6" -> {
                            PowerUp powerUp = new SpeedBoost(
                                    row * ResourceHandler.getImage(GameConstants.RESOURCE_SPEED).getWidth(),
                                    col * ResourceHandler.getImage(GameConstants.RESOURCE_SPEED).getHeight(),
                                    ResourceHandler.getImage(GameConstants.RESOURCE_SPEED)
                            );
                            gw.addToPowerUpGameCollection(powerUp);
                        }



                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
            System.exit(-2);
        }


    }

}
