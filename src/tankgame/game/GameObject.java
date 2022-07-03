package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected BufferedImage img;

    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public abstract void drawImage(Graphics g);

}
