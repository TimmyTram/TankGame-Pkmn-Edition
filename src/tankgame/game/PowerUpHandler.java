package tankgame.game;

import tankgame.game.powerups.PowerUp;

import java.awt.*;
import java.util.ArrayList;

public class PowerUpHandler {

    private final ArrayList<PowerUp> powerUps = new ArrayList<>();

    public PowerUpHandler() {}

    public void addPowerUps(PowerUp powerUp) {
        if (powerUp == null) {
            return;
        }
        this.powerUps.add(powerUp);
    }

    public boolean destroyPowerUp(PowerUp powerUp) {
        if (powerUp == null) {
            return false;
        }
        return this.powerUps.remove(powerUp);
    }

    public void drawPowerUps(Graphics g) {
        for (PowerUp powerUp : powerUps) {
            powerUp.drawImage(g);
        }
    }

    public int size() {
        return this.powerUps.size();
    }

    public PowerUp get(int index) {
        if(index > this.size()) {
            return null;
        }
        return this.powerUps.get(index);
    }

}