package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class MovableObject extends GameObject {

    protected float vx;
    protected float vy;
    protected float angle;
    protected float R;

    public MovableObject(float x, float y, float vx, float vy, float angle, float R, BufferedImage img) {
        super(x, y, img);
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.R = R;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public float getVx() {
        return this.vx;
    }

    public float getVy() {
        return this.vy;
    }

    public Rectangle getBoundsHorizontal() {
        return new Rectangle((int)(this.x + this.vx), (int)(this.y), (int)(this.width + this.vx / 2), this.height);
    }

    public Rectangle getBoundsVertical() {
        return new Rectangle((int)(this.x), (int)(this.y + this.vy), (this.width), (int)(this.height + this.vy / 2));
    }

    public Rectangle getBounds() {
        return new Rectangle((int)(this.x), (int)(this.y), (this.width), (this.height));
    }
}
