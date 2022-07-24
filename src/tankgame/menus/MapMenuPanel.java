package tankgame.menus;

import tankgame.Launcher;
import tankgame.constants.ResourceConstants;
import tankgame.game.GameWorld;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MapMenuPanel extends JPanel {

    private BufferedImage menuBackground;
    private GameWorld gamePanel;
    private JButton map1;
    private JButton map2;
    private JButton map3;
    private JButton exit;
    private Launcher lf;

    public MapMenuPanel(Launcher lf, GameWorld gamePanel) {
        this.lf = lf;
        this.gamePanel = gamePanel;
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
        map1 = createMapButtons(ResourceConstants.MAP_2FORT, 175, 50, 150, 50);
        map2 = createMapButtons(ResourceConstants.MAP_PILLAR, 175, 100, 150, 50);
        map3 = createMapButtons(ResourceConstants.MAP_TUNNELS, 175, 150, 150, 50);
        this.add(map1);
        this.add(map2);
        this.add(map3);
    }

    private String truncateMapName(String map) {
        return map.substring(0, map.indexOf("."));
    }

    private JButton createMapButtons(String map, int x, int y, int width, int height) {
        JButton result =  new JButton(this.truncateMapName(map));
        result.setFont(new Font("Courier New", Font.BOLD, 24));
        result.setBounds(x, y, width, height);
        result.setBackground(Color.black);
        result.setForeground(Color.red);
        result.setFocusPainted(false);
        result.addActionListener((actionEvent -> {
            this.gamePanel.selectMap(map);
            this.lf.setFrame("game");
        }));
        return result;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }

}
