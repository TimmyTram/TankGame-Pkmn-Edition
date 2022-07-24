package tankgame.game.moveableObjects;

import tankgame.game.Collidable;
import tankgame.game.GameObject;

import java.awt.image.BufferedImage;

public abstract class MoveableObject extends GameObject implements Collidable {

    protected float vx;
    protected float vy;
    protected float angle;
    protected float R;

    public MoveableObject(float x, float y, float vx, float vy, float angle, float R, BufferedImage img) {
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
}