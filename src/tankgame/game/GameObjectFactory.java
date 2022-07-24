package tankgame.game;

import tankgame.constants.GameObjectID;
import tankgame.constants.ResourceConstants;
import tankgame.ResourceHandler;
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
                        row * ResourceHandler.getImage(ResourceConstants.IMAGES_TANK_1).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.IMAGES_TANK_1).getHeight(),
                        0,
                        0,
                        0,
                        ResourceHandler.getImage(ResourceConstants.IMAGES_TANK_1),
                        1,
                        "Lucas",
                        gw
                );
            }

            case GameObjectID.PLAYER_2 -> {
                return new Tank(
                        row * ResourceHandler.getImage(ResourceConstants.IMAGES_TANK_2).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.IMAGES_TANK_2).getHeight(),
                        0,
                        0,
                        0,
                        ResourceHandler.getImage(ResourceConstants.IMAGES_TANK_2),
                        2,
                        "Dawn",
                        gw
                );
            }

            case GameObjectID.BORDER, GameObjectID.UNBREAKABLE_WALL -> {
                return new UnbreakableWall(
                        row * ResourceHandler.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.IMAGES_UNBREAKABLE_WALL)
                );
            }

            case GameObjectID.BREAKABLE_WALL -> {
                return new BreakableWall(
                        row * ResourceHandler.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.IMAGES_BREAKABLE_WALL)
                );
            }

            case GameObjectID.HEAL -> {
                return new Heal(
                        row * ResourceHandler.getImage(ResourceConstants.IMAGES_HEAL).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.IMAGES_HEAL).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.IMAGES_HEAL)
                );
            }

            case GameObjectID.BARRAGE -> {
                return new Barrage(
                        row * ResourceHandler.getImage(ResourceConstants.IMAGES_BARRAGE).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.IMAGES_BARRAGE).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.IMAGES_BARRAGE)
                );
            }

            case GameObjectID.SPEED_BOOST -> {
                return new SpeedBoost(
                        row * ResourceHandler.getImage(ResourceConstants.IMAGES_SPEED).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.IMAGES_SPEED).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.IMAGES_SPEED)
                );
            }

            default -> throw new IllegalArgumentException("Unknown Game object ID: " + id + " @ row: " + row + " column: " + col);
        }
    }
}
