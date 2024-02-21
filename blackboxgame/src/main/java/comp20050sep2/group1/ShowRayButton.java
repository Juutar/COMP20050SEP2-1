package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

public class ShowRayButton extends AbstractShowButton {

    public ShowRayButton(Vector2D pos){
        super(pos);
    }

    @Override
    public void setCustomTexts() {
        showText = "Set Rays";
        hideText = "Set Atoms";
    }
}
