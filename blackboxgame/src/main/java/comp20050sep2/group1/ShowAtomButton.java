package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

public class ShowAtomButton extends AbstractShowButton {

    public ShowAtomButton(Vector2D pos) {
        super(pos, "End Game", "Game Ended");
    }

    @Override
    public void performAction() {
        if (stateShow) {
            if (GamePanel.get().board.allGuessAtomsPlaced()) {
                toggleState();
                GamePanel.get().board.toggleTrueAtomsVisible();
                GamePanel.get().board.toggleRaysVisible();
                GamePanel.get().board.toggleEvaluate();
                GamePanel.get().board.toggleScore();
            } else {
                GamePanel.get().outputBox.setText("Place 6 atoms");
            }
        }
    }
}
