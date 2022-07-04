package tankgame.game;


import java.awt.*;
import java.util.ArrayList;

public class ProjectileHandler {

    private static ProjectileHandler projectileHandler = null;

    private ArrayList<Projectile> projectiles = new ArrayList<>();

    private ProjectileHandler() {}

    public static ProjectileHandler getInstance() {
        if(projectileHandler == null)
            projectileHandler = new ProjectileHandler();
        return projectileHandler;
    }

    public ArrayList<Projectile> getProjectiles() {
        return this.projectiles;
    }

    public boolean spawnProjectile(Projectile projectile) {
        if(projectile == null) {
            return false;
        }
        return projectiles.add(projectile);
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
