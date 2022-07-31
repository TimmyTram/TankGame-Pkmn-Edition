package tankgame.menus;

import tankgame.Launcher;

import javax.swing.*;

public class PowerUpMenuPanel extends MenuPanel {
    private JButton help;

    public PowerUpMenuPanel(Launcher lf) {
        super(lf, "powerups.png");
        help = super.createButton("BACK", 24, 175, 475, 175, 40, "help");
        this.add(help);
    }
}
