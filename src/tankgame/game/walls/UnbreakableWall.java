package tankgame.game.walls;

import tankgame.game.Projectile;

import java.awt.image.BufferedImage;

public class UnbreakableWall extends Wall {

    public UnbreakableWall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

    @Override
    public void setDestroyed(boolean destroyed) {

    }
}
