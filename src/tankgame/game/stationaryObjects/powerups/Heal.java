package tankgame.game.stationaryObjects.powerups;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.game.loaders.ResourceLoader;
import tankgame.game.moveableObjects.tanks.Tank;

import java.awt.image.BufferedImage;

public class Heal extends PowerUp {

    public Heal(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void empower(Tank tank) {
        tank.heal(20);
    }

    @Override
    public void playSound() {
        (new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_HEAL))).playSound();
    }
}
