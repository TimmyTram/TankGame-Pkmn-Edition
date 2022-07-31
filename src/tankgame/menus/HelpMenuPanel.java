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
    private JButton viewCheatCodes;
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

        viewKeyBinds = createButton("KEYBIND", 24, 175, 40, 175, 40, "keybind");
        viewPowerUps = createButton("POWER UPS", 24, 175, 90, 175, 40, "powerups");
        viewCheatCodes = createButton("CHEAT CODES", 20, 175, 140, 175, 40, "cheatCodes");
        mainMenu = createButton("BACK", 24, 175, 450, 175, 40, "start");

        this.add(viewKeyBinds);
        this.add(viewPowerUps);
        this.add(viewCheatCodes);
        this.add(mainMenu);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
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
