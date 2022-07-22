package tankgame.game;

import tankgame.ResourceConstants;
import tankgame.ResourceHandler;
import tankgame.game.powerups.Barrage;
import tankgame.game.powerups.Heal;
import tankgame.game.powerups.SpeedBoost;
import tankgame.game.tanks.Tank;
import tankgame.game.walls.BreakableWall;
import tankgame.game.walls.UnbreakableWall;

public class GameObjectFactory {
    public GameObject createGameObject(String id, int row, int col, GameWorld gw) {
        if(id == null || id.isEmpty() || GameObjectID.EMPTY.equals(id)) {
            return null;
        }

        switch(id) {

            case GameObjectID.PLAYER_1 -> {
                return new Tank(
                        row * ResourceHandler.getImage(ResourceConstants.RESOURCE_TANK_1).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.RESOURCE_TANK_1).getHeight(),
                        0,
                        0,
                        0,
                        ResourceHandler.getImage(ResourceConstants.RESOURCE_TANK_1),
                        "Lucas",
                        gw
                );
            }

            case GameObjectID.PLAYER_2 -> {
                return new Tank(
                        row * ResourceHandler.getImage(ResourceConstants.RESOURCE_TANK_2).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.RESOURCE_TANK_2).getHeight(),
                        0,
                        0,
                        0,
                        ResourceHandler.getImage(ResourceConstants.RESOURCE_TANK_2),
                        "Dawn",
                        gw
                );
            }

            case GameObjectID.BORDER, GameObjectID.UNBREAKABLE_ROCK -> {
                return new UnbreakableWall(
                        row * ResourceHandler.getImage(ResourceConstants.RESOURCE_UNBREAKABLE_WALL).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.RESOURCE_UNBREAKABLE_WALL).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.RESOURCE_UNBREAKABLE_WALL)
                );
            }

            case GameObjectID.BREAKABLE_ROCK -> {
                return new BreakableWall(
                        row * ResourceHandler.getImage(ResourceConstants.RESOURCE_BREAKABLE_WALL).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.RESOURCE_BREAKABLE_WALL).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.RESOURCE_BREAKABLE_WALL)
                );
            }

            case GameObjectID.HEAL -> {
                return new Heal(
                        row * ResourceHandler.getImage(ResourceConstants.RESOURCE_HEAL).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.RESOURCE_HEAL).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.RESOURCE_HEAL)
                );
            }

            case GameObjectID.BARRAGE -> {
                return new Barrage(
                        row * ResourceHandler.getImage(ResourceConstants.RESOURCE_BARRAGE).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.RESOURCE_BARRAGE).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.RESOURCE_BARRAGE)
                );
            }

            case GameObjectID.SPEED_BOOST -> {
                return new SpeedBoost(
                        row * ResourceHandler.getImage(ResourceConstants.RESOURCE_SPEED).getWidth(),
                        col * ResourceHandler.getImage(ResourceConstants.RESOURCE_SPEED).getHeight(),
                        ResourceHandler.getImage(ResourceConstants.RESOURCE_SPEED)
                );
            }

            default -> throw new IllegalArgumentException("Unknown Game object ID: " + id + " @ row: " + row + " column: " + col);
        }
    }
}
