package tankgame.game.stationaryObjects.walls;

import tankgame.constants.ResourceConstants;
import tankgame.ResourceHandler;

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
        ResourceHandler.getSound(ResourceConstants.RESOURCE_ROCK_SMASH_SOUND).play();
    }

}
