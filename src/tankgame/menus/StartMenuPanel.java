package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartMenuPanel extends JPanel {
    private BufferedImage menuBackground;
    private JButton start;

    private JButton maps;
    private JButton help;
    private JButton exit;
    private Launcher lf;

    public StartMenuPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResource("menu/title.png"),
                            String.format("Could not find %s", "menu/title.png"))
            );
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        start = new JButton("Start");
        start.setFont(new Font("Courier New", Font.BOLD, 24));
        start.setBounds(175, 320, 175, 40);
        start.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        start.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        start.setFocusPainted(false);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));

        maps = new JButton("Maps");
        maps.setFont(new Font("Courier New", Font.BOLD, 24));
        maps.setBounds(175, 370, 175, 40);
        maps.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        maps.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        maps.setFocusPainted(false);
        maps.addActionListener((actionEvent -> {
            this.lf.setFrame("maps");
        }));

        help = new JButton("Help");
        help.setFont(new Font("Courier New", Font.BOLD, 24));
        help.setBounds(175, 420, 175, 40);
        help.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        help.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        help.setFocusPainted(false);
        help.addActionListener((actionEvent -> {
            this.lf.setFrame("help");
        }));

        exit = new JButton("Exit");
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

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
