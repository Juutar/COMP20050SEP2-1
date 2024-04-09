package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;

public class ShowAtomButton extends AbstractShowButton {

    private boolean ended = false;

    public ShowAtomButton(Vector2D pos) {
        super(pos, "End Game", "Game Ended");
    }

    @Override
    public void performAction() {
        if (ended) {
            MainMenuPanel.get().maxScore = Math.min(MainMenuPanel.get().maxScore, GamePanel.get().board.getScore());
            GamePanel.destroy();
            MainMenuPanel.get().gameFrame = null;
            SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(true);
            return;
        }

        if (stateShow) {
            if (GamePanel.get().board.allGuessAtomsPlaced()) {
                toggleState();
                GamePanel.get().board.toggleTrueAtomsVisible();
                GamePanel.get().board.toggleRaysVisible();
                GamePanel.get().board.toggleEvaluate();
                GamePanel.get().board.toggleScore();
                ended = true;
                setButtonText("Quit to menu");
            } else {
                GamePanel.get().outputBox.setText("Place 6 atoms");
            }
        }
    }
}
