package tankgame.game;

import tankgame.constants.GameObjectID;
import tankgame.constants.ResourceConstants;
import tankgame.game.loaders.ResourceLoader;
import tankgame.game.stationaryObjects.powerups.Barrage;
import tankgame.game.stationaryObjects.powerups.Heal;
import tankgame.game.stationaryObjects.powerups.SpeedBoost;
import tankgame.game.moveableObjects.tanks.Tank;
import tankgame.game.stationaryObjects.walls.BreakableWall;
import tankgame.game.stationaryObjects.walls.UnbreakableWall;

public class GameObjectFactory {
    public GameObject createGameObject(String id, int row, int col, GameWorld gw) {
        if(id == null || id.isEmpty() || GameObjectID.EMPTY.equals(id)) {
            return null;
        }

        switch(id) {

            case GameObjectID.PLAYER_1 -> {
                return new Tank(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_TANK_ARROW).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_TANK_ARROW).getHeight(),
                        0,
                        0,
                        0,
                        ResourceLoader.getImage(ResourceConstants.IMAGES_TANK_ARROW),
                        1,
                        "DAWN",
                        gw
                );
            }

            case GameObjectID.PLAYER_2 -> {
                return new Tank(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_TANK_ARROW).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_TANK_ARROW).getHeight(),
                        0,
                        0,
                        0,
                        ResourceLoader.getImage(ResourceConstants.IMAGES_TANK_ARROW),
                        2,
                        "BIDOOF",
                        gw
                );
            }

            case GameObjectID.BORDER -> {
                return new UnbreakableWall(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_BORDER_WALL).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_BORDER_WALL).getHeight(),
                        ResourceLoader.getImage(ResourceConstants.IMAGES_BORDER_WALL)
                );
            }

            case GameObjectID.UNBREAKABLE_WALL -> {
                return new UnbreakableWall(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight(),
                        ResourceLoader.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL)
                );
            }

            case GameObjectID.BREAKABLE_WALL -> {
                return new BreakableWall(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL).getHeight(),
                        ResourceLoader.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL)
                );
            }

            case GameObjectID.HEAL -> {
                return new Heal(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_HEAL).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_HEAL).getHeight(),
                        ResourceLoader.getImage(ResourceConstants.IMAGES_HEAL)
                );
            }

            case GameObjectID.BARRAGE -> {
                return new Barrage(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_BARRAGE).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_BARRAGE).getHeight(),
                        ResourceLoader.getImage(ResourceConstants.IMAGES_BARRAGE)
                );
            }

            case GameObjectID.SPEED_BOOST -> {
                return new SpeedBoost(
                        row * ResourceLoader.getImage(ResourceConstants.IMAGES_SPEED).getWidth(),
                        col * ResourceLoader.getImage(ResourceConstants.IMAGES_SPEED).getHeight(),
                        ResourceLoader.getImage(ResourceConstants.IMAGES_SPEED)
                );
            }

            default -> throw new IllegalArgumentException("Unknown Game object ID: " + id + " @ row: " + row + " column: " + col);
        }
    }
}
