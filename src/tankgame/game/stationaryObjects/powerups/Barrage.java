package tankgame.game.stationaryObjects.powerups;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.game.loaders.ResourceLoader;
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
        //ResourceHandler.getSound(ResourceConstants.RESOURCE_BARRAGE_SOUND).play();
        (new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_BARRAGE))).playSound();
    }
}
