package tankgame.game.walls;

import tankgame.game.Collidable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableWall extends Wall {

    public BreakableWall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void checkCollision(Collidable obj) {

    }
}
