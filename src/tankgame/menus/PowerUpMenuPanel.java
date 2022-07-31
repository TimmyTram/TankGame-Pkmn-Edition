package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PowerUpMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton help;
    private Launcher lf;

    public PowerUpMenuPanel(Launcher lf) {
        this.lf = lf;
        try {
            menuBackground = ImageIO.read(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResource("menu/powerups.png"),
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
        help.setBounds(175, 475, 175, 30);
        help.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        help.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        help.setFocusPainted(false);
        help.addActionListener((actionEvent -> {
            this.lf.setFrame("help");
        }));

        this.add(help);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }

}
