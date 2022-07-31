package tankgame.game.moveableObjects.projectiles;

import tankgame.game.moveableObjects.tanks.Tank;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Projectile {

    public Bullet(float x, float y, float vx, float vy, float angle, float R, BufferedImage img, Tank ownership) {
        super(x, y, vx, vy, angle, R, img, ownership);
    }

    @Override
    public void playSound() {
        this.ownership.getBulletCollideSound().playSound();
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, (int) x, (int) y, null);

//        g2d.setColor(Color.yellow);
//        g2d.drawRect((int)x,(int)y,this.img.getWidth(), this.img.getHeight());
    }
}
