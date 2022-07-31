package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class KeyBindMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton help;
    private Launcher lf;
    private static final int STARTING_X = 15;
    private static final int STARTING_Y = 20;
    private static final int WIDTH = 150;
    private static final int HEIGHT = 50;

    public KeyBindMenuPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResource("menu/map_selection.png"),
                            String.format("Could not find %s", "menu/title.png"))
            );
        } catch (IOException e) {
            System.out.println("Error cant read menu background");
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        help = new JButton("BACK");
        help.setFont(new Font("Courier New", Font.BOLD, 24));
        help.setBounds(175, 450, 175, 40);
        help.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        help.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        help.setFocusPainted(false);
        help.addActionListener((actionEvent -> {
            this.lf.setFrame("help");
        }));

        String[] labels = {"CONTROL", "FORWARD", "BACKWARD", "ROTATE LEFT", "ROTATE RIGHT", "SHOOT"};
        for(int i = 0; i < labels.length; i++) {
            this.add(createLabel(labels[i], 20, STARTING_X, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
        }

        labels = new String[]{"PLAYER 1", "W", "S", "A", "D", "SPACEBAR"};
        for(int i = 0; i < labels.length; i++) {
            this.add(createLabel(labels[i], 20, STARTING_X + WIDTH, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
        }

        labels = new String[]{"PLAYER 2", "↑", "↓", "↓", "→", "ENTER"};
        for(int i = 0; i < labels.length; i++) { // Yes, this is not the best but its GUI Code, I hate making it scalable.
            if(labels[i].equalsIgnoreCase("PLAYER 2") || labels[i].equalsIgnoreCase("ENTER")) {
                this.add(createLabel(labels[i], 20, STARTING_X + WIDTH * 2, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
            } else {
                this.add(createLabel(labels[i], 48, STARTING_X + WIDTH * 2, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
            }
        }
        this.add(help);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }

    private JLabel createLabel(String text, int textSize, int x, int y, int width, int height) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Courier New", Font.BOLD, textSize));
        label.setBounds(x, y, width, height);
        label.setOpaque(true);
        label.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        label.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        return label;
    }

}
