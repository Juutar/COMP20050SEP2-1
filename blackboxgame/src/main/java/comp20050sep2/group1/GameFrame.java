package comp20050sep2.group1;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Demo game");

        add(GamePanel.get());

        pack();

        setLocationRelativeTo(null);
        setVisible(true);

        GamePanel.get().startGameThread();
    }
}
