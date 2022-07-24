package tankgame.game.stationaryObjects.powerups;

import tankgame.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.ResourceHandler;
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
        (new Sound(ResourceHandler.getSound(ResourceConstants.SOUND_SPEED))).playSound();
    }
}
