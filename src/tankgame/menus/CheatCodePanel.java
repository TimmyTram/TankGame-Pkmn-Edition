package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CheatCodePanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton help;
    private Launcher lf;
    private static final int STARTING_X = 15;
    private static final int STARTING_Y = 20;
    private static final int WIDTH = 225;
    private static final int HEIGHT = 50;


    public CheatCodePanel(Launcher lf) {
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

        String[] labels = {"CHEAT CODES", "RENDER HITBOXES", "ONE SHOT ONE KILL"};
        for(int i = 0; i < labels.length; i++) {
            this.add(createLabel(labels[i], 22, STARTING_X, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
        }

        labels = new String[]{"KEY BINDS", "F1", "F2"};
        for(int i = 0; i < labels.length; i++) {
            this.add(createLabel(labels[i], 22, STARTING_X + WIDTH, STARTING_Y + (HEIGHT * i), WIDTH, HEIGHT));
        }

        help = createButton("BACK", 24, 175, 450, 175, 40, "help");
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

    private JButton createButton(String text, int textSize, int x, int y, int width, int height, String frame) {
        JButton result = new JButton(text);
        result.setFont(new Font("Courier New", Font.BOLD, textSize));
        result.setBounds(x, y, width, height);
        result.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        result.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        result.setFocusPainted(false);
        result.addActionListener((actionEvent -> {
            this.lf.setFrame(frame);
        }));
        return result;
    }

}
