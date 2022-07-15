package tankgame.game.powerups;

import tankgame.GameConstants;
import tankgame.ResourceHandler;
import tankgame.game.Collidable;
import tankgame.game.tanks.Tank;

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
        ResourceHandler.getSound(GameConstants.RESOURCE_HEAL_SOUND).play();
    }

    @Override
    public void checkCollision(Collidable obj) {

    }
}
