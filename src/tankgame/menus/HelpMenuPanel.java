package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.GameConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HelpMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private JButton mainMenu;
    private JButton viewPowerUps;
    private JButton viewKeyBinds;
    private Launcher lf;

    public HelpMenuPanel(Launcher lf) {
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

        viewKeyBinds = new JButton("KEYBINDS");
        viewKeyBinds.setFont(new Font("Courier New", Font.BOLD, 24));
        viewKeyBinds.setBounds(175, 40, 175, 40);
        viewKeyBinds.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        viewKeyBinds.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        viewKeyBinds.setFocusPainted(false);
        viewKeyBinds.addActionListener((actionEvent -> {
            this.lf.setFrame("keybind");
        }));

        viewPowerUps = new JButton("POWER UPS");
        viewPowerUps.setFont(new Font("Courier New", Font.BOLD, 24));
        viewPowerUps.setBounds(175, 90, 175, 40);
        viewPowerUps.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        viewPowerUps.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        viewPowerUps.setFocusPainted(false);
        viewPowerUps.addActionListener((actionEvent -> {
            this.lf.setFrame("powerups");
        }));

        mainMenu = new JButton("BACK");
        mainMenu.setFont(new Font("Courier New", Font.BOLD, 24));
        mainMenu.setBounds(175, 450, 175, 40);
        mainMenu.setBackground(Color.decode(GameConstants.BUTTON_BACKGROUND_COLOR));
        mainMenu.setForeground(Color.decode(GameConstants.BUTTON_FOREGROUND_COLOR));
        mainMenu.setFocusPainted(false);
        mainMenu.addActionListener((actionEvent -> {
            this.lf.setFrame("start");
        }));

        this.add(viewKeyBinds);
        this.add(viewPowerUps);
        this.add(mainMenu);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }

}
