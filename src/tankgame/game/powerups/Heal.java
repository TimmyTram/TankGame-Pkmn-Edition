package tankgame.game.powerups;

import tankgame.game.Collidable;
import tankgame.game.Tank;

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
    public void checkCollision(Collidable obj) {

    }
}
