package tankgame.game.powerups;

import tankgame.game.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedBoost extends PowerUp {

    public SpeedBoost(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void checkCollision(Collidable obj) {

    }
}
