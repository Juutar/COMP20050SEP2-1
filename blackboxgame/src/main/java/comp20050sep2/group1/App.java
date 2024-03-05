package comp20050sep2.group1;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Demo game");

        window.add(GamePanel.get());

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        GamePanel.get().startGameThread();
    }
}
