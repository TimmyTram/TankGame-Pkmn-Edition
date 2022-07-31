package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class MenuPanel extends JPanel {

    protected BufferedImage menuBackground;
    protected Launcher lf;
    protected final int STARTING_X = 15;
    protected final int STARTING_Y = 20;
    protected final int WIDTH = 150;
    protected final int HEIGHT = 50;

    public MenuPanel(Launcher lf, String menuBackgroundName) {
        this.lf = lf;
        try {
            this.menuBackground = ImageIO.read(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResource("menu/" + menuBackgroundName),
                            String.format("Could not find %s", "menu/" + menuBackgroundName))
            );
        } catch (IOException e) {
            System.out.println("Error cant read: " + menuBackgroundName);
            e.printStackTrace();
            System.exit(-3);
        }
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }

    protected JLabel createLabel(String text, int textSize, int x, int y, int width, int height) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Courier New", Font.BOLD, textSize));
        label.setBounds(x, y, width, height);
        label.setOpaque(true);
        label.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        label.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        return label;
    }

    protected JButton createButton(String text, int textSize, int x, int y, int width, int height, String frame) {
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
