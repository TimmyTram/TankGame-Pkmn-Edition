package tankgame.game;

import tankgame.GameConstants;
import tankgame.ResourceHandler;
import tankgame.game.powerups.Barrage;
import tankgame.game.powerups.Heal;
import tankgame.game.powerups.SpeedBoost;
import tankgame.game.walls.BreakableWall;
import tankgame.game.walls.UnbreakableWall;

public class GameObjectFactory {
    public GameObject createGameObject(String id, int row, int col) {
        if(id == null || id.isEmpty() || "0".equals(id)) {
            return null;
        }

        switch(id) {
            case "3", "9" -> {
                return new UnbreakableWall(
                        row * ResourceHandler.getImage(GameConstants.RESOURCE_UNBREAKABLE_WALL).getWidth(),
                        col * ResourceHandler.getImage(GameConstants.RESOURCE_UNBREAKABLE_WALL).getHeight(),
                        ResourceHandler.getImage(GameConstants.RESOURCE_UNBREAKABLE_WALL)
                );
            }

            case "2" -> {
                return new BreakableWall(
                        row * ResourceHandler.getImage(GameConstants.RESOURCE_BREAKABLE_WALL).getWidth(),
                        col * ResourceHandler.getImage(GameConstants.RESOURCE_BREAKABLE_WALL).getHeight(),
                        ResourceHandler.getImage(GameConstants.RESOURCE_BREAKABLE_WALL)
                );
            }

            case "4" -> {
                return new Heal(
                        row * ResourceHandler.getImage(GameConstants.RESOURCE_HEAL).getWidth(),
                        col * ResourceHandler.getImage(GameConstants.RESOURCE_HEAL).getHeight(),
                        ResourceHandler.getImage(GameConstants.RESOURCE_HEAL)
                );
            }

            case "5" -> {
                return new Barrage(
                        row * ResourceHandler.getImage(GameConstants.RESOURCE_BARRAGE).getWidth(),
                        col * ResourceHandler.getImage(GameConstants.RESOURCE_BARRAGE).getHeight(),
                        ResourceHandler.getImage(GameConstants.RESOURCE_BARRAGE)
                );
            }

            case "6" -> {
                return new SpeedBoost(
                        row * ResourceHandler.getImage(GameConstants.RESOURCE_SPEED).getWidth(),
                        col * ResourceHandler.getImage(GameConstants.RESOURCE_SPEED).getHeight(),
                        ResourceHandler.getImage(GameConstants.RESOURCE_SPEED)
                );
            }

            default -> throw new IllegalArgumentException("Unknown Game object ID: " + id + " @ row: " + row + " column: " + col);
        }
    }
}
