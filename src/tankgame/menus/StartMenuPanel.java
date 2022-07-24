package tankgame.menus;

import tankgame.Launcher;

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
        start.setBounds(175, 330, 175, 50);
        start.setBackground(Color.black);
        start.setForeground(Color.red);
        start.setFocusPainted(false);
        start.addActionListener((actionEvent -> {
            this.lf.setFrame("game");
        }));

        maps = new JButton("Maps");
        maps.setFont(new Font("Courier New", Font.BOLD, 24));
        maps.setBounds(175, 390, 175, 50);
        maps.setBackground(Color.black);
        maps.setForeground(Color.red);
        maps.setFocusPainted(false);
        maps.addActionListener((actionEvent -> {
            this.lf.setFrame("maps");
        }));

        exit = new JButton("Exit");
        exit.setSize(new Dimension(200, 100));
        exit.setFont(new Font("Courier New", Font.BOLD, 24));
        exit.setBounds(175, 450, 175, 50);
        exit.setBackground(Color.black);
        exit.setForeground(Color.red);
        exit.setFocusPainted(false);
        exit.addActionListener((actionEvent -> {
            this.lf.closeGame();
        }));

        this.add(start);
        this.add(maps);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground, 0, 0, null);
    }
}
