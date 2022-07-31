package tankgame.game.stationaryObjects.walls;

import tankgame.util.Sound;
import tankgame.constants.ResourceConstants;
import tankgame.game.loaders.ResourceLoader;

import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {

    public BreakableWall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void setDestroyed(boolean status) {
        super.isDestroyed = status;
    }

    public void playSound() {
        (new Sound(ResourceLoader.getSound(ResourceConstants.SOUND_ROCK_SMASH))).playSound();
    }

}
