package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

public class ShowAtomButton extends AbstractShowButton {

    public ShowAtomButton(Vector2D pos) {
        super(pos);
    }

    @Override
    public void setCustomTexts() {
        showText = "End Game";
        hideText = "Game Ended";
    }

    @Override
    public void performAction() {
        if (GamePanel.get().board.allGuessAtomsPlaced() && this.stateShow) {
            toggleState();
            GamePanel.get().board.toggleTrueAtomsVisible();
            GamePanel.get().board.toggleRaysVisible();
            GamePanel.get().board.toggleEvaluate();
            GamePanel.get().board.toggleScore();
            //must stop game
        } else {
            GamePanel.get().outputBox.setText("Place 6 atoms");
        }
    }
}
