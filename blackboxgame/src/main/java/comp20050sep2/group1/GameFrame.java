package comp20050sep2.group1;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        addWindowListener(MainMenuPanel.get());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("BlackBox Game");

        add(GamePanel.get());

        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        GamePanel.get().startGameThread();
    }
}
