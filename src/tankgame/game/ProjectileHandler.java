package tankgame.game;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileHandler {

    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    public ProjectileHandler() {}

    public void spawnProjectile(Projectile projectile) {
        if(projectile == null) {
            return;
        }
        projectiles.add(projectile);
    }

    public boolean destroyProjectile(Projectile projectile) {
        if(projectile == null) {
            return false;
        }
        return projectiles.remove(projectile);
    }

    public void update() {
        for(int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).update();
        }
    }

    public void drawProjectiles(Graphics g) {
        for(Projectile projectile : projectiles) {
            projectile.drawImage(g);
        }
    }

    public int size() {
        return this.projectiles.size();
    }

    public Projectile get(int index) {
        if(index > this.size()) {
            return null;
        }
        return this.projectiles.get(index);
    }

}
