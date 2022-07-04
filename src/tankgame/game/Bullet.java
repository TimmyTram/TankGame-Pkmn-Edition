package tankgame.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends Projectile {

    public Bullet(float x, float y, float vx, float vy, float angle, float R, BufferedImage img) {
        super(x, y, vx, vy, angle, R, img);
        getImageDimension();
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, (int) x, (int) y, null);
    }
}
