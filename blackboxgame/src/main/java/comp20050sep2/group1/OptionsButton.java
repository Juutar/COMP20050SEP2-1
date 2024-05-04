package comp20050sep2.group1;

import javax.swing.SwingUtilities;

import comp20050sep2.group1.utils.Vector2D;

public class OptionsButton extends AbstractButton {

    /**
     * Constructs button to go to options panel
     * @param pos Position of the button
     */
    public OptionsButton(Vector2D pos) {
        super(pos, "Options");
    }

    @Override
    public void performAction() {
        
        SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(false);
        new OptionFrame();
    }
}
