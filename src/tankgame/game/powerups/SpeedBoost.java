package tankgame.game.powerups;

import tankgame.GameConstants;
import tankgame.ResourceHandler;
import tankgame.game.Collidable;
import tankgame.game.Tank;

import java.awt.image.BufferedImage;

public class SpeedBoost extends PowerUp {

    public SpeedBoost(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void empower(Tank tank) {
        tank.changeSpeed(4);
    }

    @Override
    public void playSound() {
        ResourceHandler.getSound(GameConstants.RESOURCE_SPEED_SOUND).play();
    }

    @Override
    public void checkCollision(Collidable obj) {

    }
}
