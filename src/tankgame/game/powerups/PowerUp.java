package tankgame.game.powerups;

import tankgame.game.Collidable;
import tankgame.game.GameObject;

import java.awt.image.BufferedImage;

public abstract class PowerUp extends GameObject implements Collidable {
    public PowerUp(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void checkCollision(Collidable obj) {

    }
}
