package tankgame.menus;

import tankgame.Launcher;

import javax.swing.*;

public class KeyBindMenuPanel extends MenuPanel {

    private JButton help;

    public KeyBindMenuPanel(Launcher lf) {
        super(lf, "map_selection.png");

        help = super.createButton("BACK", 24, 175, 450, 175, 40, "help");

        String[] labels = {"CONTROL", "FORWARD", "BACKWARD", "ROTATE LEFT", "ROTATE RIGHT", "SHOOT"};
        for (int i = 0; i < labels.length; i++) {
            this.add(super.createLabel(labels[i], 20, STARTING_X, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
        }

        labels = new String[]{"PLAYER 1", "W", "S", "A", "D", "SPACEBAR"};
        for (int i = 0; i < labels.length; i++) {
            this.add(super.createLabel(labels[i], 20, STARTING_X + WIDTH, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
        }

        labels = new String[]{"PLAYER 2", "↑", "↓", "↓", "→", "ENTER"};
        for (int i = 0; i < labels.length; i++) { // Yes, this is not the best but its GUI Code, I hate making it scalable for text.
            if (labels[i].equalsIgnoreCase("PLAYER 2") || labels[i].equalsIgnoreCase("ENTER")) {
                this.add(super.createLabel(labels[i], 20, STARTING_X + WIDTH * 2, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
            } else {
                this.add(super.createLabel(labels[i], 48, STARTING_X + WIDTH * 2, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
            }
        }
        this.add(help);
    }
}
