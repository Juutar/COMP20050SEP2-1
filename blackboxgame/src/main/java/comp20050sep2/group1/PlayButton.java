package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;

public class PlayButton extends AbstractButton {

    public PlayButton(Vector2D pos) {
        super(pos, "Play");
    }

    @Override
    public void performAction() {
        if (getText().equals("Play")) {
            setButtonText("Replay");
        }
        new GameFrame();
        SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(false);
    }
}
