package tankgame.game.projectiles;

import tankgame.game.MovableObject;
import tankgame.game.tanks.Tank;

import java.awt.image.BufferedImage;

public abstract class Projectile extends MovableObject {

    private Tank ownership;

    public Projectile(float x, float y, float vx, float vy, float angle, float R, BufferedImage img, Tank ownership) {
        super(x, y, vx, vy, angle, R, img);
        this.ownership = ownership;
    }

    @Override
    public void update() {
        vx = Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }

    public Tank getOwnership() {
        return this.ownership;
    }

}
