package tankgame.game.powerups;

import tankgame.game.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Barrage extends PowerUp {

    public Barrage(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void checkCollision(Collidable obj) {

    }
}
