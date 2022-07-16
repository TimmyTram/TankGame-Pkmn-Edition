package tankgame.display;

import tankgame.game.tanks.Tank;

import java.awt.*;

public class GameHUD {

    private final Tank tank;

    public GameHUD(Tank tank) {
        this.tank = tank;
    }

    public void drawHUD() {

    }

    private void drawLives(Graphics2D g2) {
        int lives = this.tank.getLives();
    }

    private void drawHealthBar(Graphics2D g2) {
        int healthPoints = this.tank.getCurrentHealthPoints();


    }

}
