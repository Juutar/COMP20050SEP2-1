package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;

public class ExitToMenuButton extends AbstractShowButton {

    /**
     * Constructs an abstract button which exits to menu
     * @param pos Position of the button
     */
    public ExitToMenuButton(Vector2D pos) {
        super(pos, "Go back to main menu", "Are you sure?");
    }

    @Override
    public void performAction() {
        toggleState();
        setButtonText("Are you sure?");

        if (stateShow) {
            GamePanel.destroy();
            MainMenuPanel.get().gameFrame = null;
            SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(true);
        }
    }
}
