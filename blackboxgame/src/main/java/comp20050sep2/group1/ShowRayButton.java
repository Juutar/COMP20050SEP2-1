package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

public class ShowRayButton extends AbstractShowButton {

    /**
     * Constructs the show rays button
     * @param pos Position of the button
     */
    public ShowRayButton(Vector2D pos) { super(pos, "Set Rays", "Set Atoms"); }

    @Override
    public void performAction() {
        toggleState();
        GamePanel.get().board.toggleAtomSelectorOn();
    }
}
