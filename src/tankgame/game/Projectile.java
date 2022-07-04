package tankgame.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Projectile extends MovableObject {

    public Projectile(float x, float y, float vx, float vy, float angle, float R, BufferedImage img) {
        super(x, y, vx, vy, angle, R, img);
    }

    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }
}
