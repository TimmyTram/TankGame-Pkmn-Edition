package tankgame.menus;

import tankgame.Launcher;

import javax.swing.*;

public class HelpMenuPanel extends MenuPanel {

    private JButton mainMenu;
    private JButton viewPowerUps;
    private JButton viewKeyBinds;
    private JButton viewCheatCodes;

    public HelpMenuPanel(Launcher lf) {
        super(lf, "menu.png");
        viewKeyBinds = super.createButton("KEYBIND", 24, 175, 40, 175, 40, "keybind");
        viewPowerUps = super.createButton("POWER UPS", 24, 175, 90, 175, 40, "powerups");
        viewCheatCodes = super.createButton("CHEAT CODES", 20, 175, 140, 175, 40, "cheatCodes");
        mainMenu = super.createButton("BACK", 24, 175, 450, 175, 40, "start");
        this.add(viewKeyBinds);
        this.add(viewPowerUps);
        this.add(viewCheatCodes);
        this.add(mainMenu);
    }
}
