package comp20050sep2.group1;

import javax.swing.*;

public class MainMenuFrame extends JFrame {

    /**
     * Constructs the main menu jframe
     */
    public MainMenuFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Main Menu");

        add(MainMenuPanel.get());

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    

}
