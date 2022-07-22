package tankgame.game.stationaryObjects.powerups;

import tankgame.constants.ResourceConstants;
import tankgame.ResourceHandler;
import tankgame.game.moveableObjects.tanks.Tank;

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
        ResourceHandler.getSound(ResourceConstants.RESOURCE_BARRAGE_SOUND).play();
    }
}
