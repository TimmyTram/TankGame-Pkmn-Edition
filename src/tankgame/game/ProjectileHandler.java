package tankgame.game;


import java.awt.*;
import java.util.ArrayList;

public class ProjectileHandler {

    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public ProjectileHandler() {}

    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }

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

}
