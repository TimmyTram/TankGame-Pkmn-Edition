package tankgame.game.stationaryObjects.powerups;

import tankgame.constants.ResourceConstants;
import tankgame.ResourceHandler;
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
        ResourceHandler.getSound(ResourceConstants.RESOURCE_HEAL_SOUND).play();
    }
}
