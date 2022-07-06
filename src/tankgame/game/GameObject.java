package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected BufferedImage img;
    protected int width;
    protected int height;

    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        getImageDimension();
    }

    public abstract void drawImage(Graphics g);

    protected void getImageDimension() {
        width = this.img.getWidth();
        height = this.img.getHeight();
    }

    public Rectangle getBounds() {
        return new Rectangle((int) this.x, (int) this.y, this.width, this.height);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public abstract void update();

}
