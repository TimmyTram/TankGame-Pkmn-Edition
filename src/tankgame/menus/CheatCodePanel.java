package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CheatCodePanel extends MenuPanel {
    private JButton help;
    private final int WIDTH = 225;
    private final int HEIGHT = 50;


    public CheatCodePanel(Launcher lf) {
        super(lf, "map_selection.png");

        String[] labels = {"CHEAT CODES", "RENDER HITBOXES", "ONE SHOT ONE KILL"};
        for(int i = 0; i < labels.length; i++) {
            this.add(super.createLabel(labels[i], 22, STARTING_X, STARTING_Y + (HEIGHT * i), this.WIDTH, HEIGHT));
        }

        labels = new String[]{"KEY BINDS", "F1", "F2"};
        for(int i = 0; i < labels.length; i++) {
            this.add(super.createLabel(labels[i], 22, STARTING_X + this.WIDTH, STARTING_Y + (HEIGHT * i), this.WIDTH, HEIGHT));
        }

        help = super.createButton("BACK", 24, 175, 450, 175, 40, "help");
        this.add(help);
    }
}
