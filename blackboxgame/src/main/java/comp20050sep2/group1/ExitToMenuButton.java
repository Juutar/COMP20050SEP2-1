package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;

public class ExitToMenuButton extends AbstractShowButton {

    public ExitToMenuButton(Vector2D pos) {
        super(pos, "Ragequit", "Are you sure?");
    }

    @Override
    public void performAction() {
        toggleState();
        if (getText().equals("Ragequit"))
            setButtonText("Are you sure?");

        if (stateShow) {
            GamePanel.destroy();
            MainMenuPanel.get().gameFrame = null;
            SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(true);
        }
    }
}
