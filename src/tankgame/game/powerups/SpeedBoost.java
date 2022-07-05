package tankgame.game.powerups;

import tankgame.game.Collidable;
import tankgame.game.Tank;

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
    public void checkCollision(Collidable obj) {

    }
}
