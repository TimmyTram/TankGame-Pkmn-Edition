package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;


import javax.swing.*;
import java.awt.*;

public class StartMenuPanel extends MenuPanel {
    private JButton start;
    private JButton maps;
    private JButton help;
    private JButton exit;

    public StartMenuPanel(Launcher lf) {
        super(lf, "title.png");
        start = super.createButton("START", 24, 175, 320, 175, 40, "game");
        maps = super.createButton("MAPS", 24, 175, 370, 175, 40, "maps");
        help = super.createButton("HELP", 24, 175, 420, 175, 40, "help");
        exit = new JButton("EXIT");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(175, 470, 175, 40);
        exit.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        exit.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        exit.setFocusPainted(false);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(start);
        this.add(maps);
        this.add(help);
        this.add(exit);
    }
}
