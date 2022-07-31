package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected float x;
    protected float y;
    protected BufferedImage img;
    protected Rectangle hitBox;

    public GameObject(float x, float y, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.hitBox = new Rectangle((int) x, (int) y, this.img.getWidth(), this.img.getHeight());
    }

    public abstract void drawImage(Graphics g);

    public abstract void drawHitbox(Graphics g);

    public Rectangle getHitBox() {
        return this.hitBox.getBounds();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public abstract void update();

}
