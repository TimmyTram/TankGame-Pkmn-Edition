package tankgame.game;

import java.awt.image.BufferedImage;

public abstract class Wall extends GameObject implements Collidable {

    public Wall(float x, float y, BufferedImage img) {
        super(x, y, img);
    }

}
