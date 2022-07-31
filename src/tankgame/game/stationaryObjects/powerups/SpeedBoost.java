package tankgame.game.stationaryObjects.powerups;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.game.loaders.ResourceLoader;
import tankgame.game.moveableObjects.tanks.Tank;

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
        (new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_SPEED))).playSound();
    }
}
