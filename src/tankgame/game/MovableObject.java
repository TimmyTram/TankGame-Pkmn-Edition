package tankgame.game;

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
}
