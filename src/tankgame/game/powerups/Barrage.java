package tankgame.game.powerups;

import tankgame.GameConstants;
import tankgame.ResourceHandler;
import tankgame.game.Collidable;
import tankgame.game.Tank;

import java.awt.image.BufferedImage;

public class Barrage extends PowerUp {

    public Barrage(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void empower(Tank tank) {
        tank.changeDelayBetweenShots(10);
    }

    @Override
    public void playSound() {
        ResourceHandler.getSound(GameConstants.RESOURCE_BARRAGE_SOUND).play();
    }

    @Override
    public void checkCollision(Collidable obj) {

    }
}
