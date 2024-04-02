package comp20050sep2.group1;

import javax.swing.*;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Blackbox Main Menu");

        add(MainMenuPanel.get());

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
