package comp20050sep2.group1;

import javax.swing.SwingUtilities;

import comp20050sep2.group1.utils.Vector2D;

public class OptionReturnButton extends AbstractButton{

    /**
     * Constructs return button on options panel
     * @param pos Position of button
     * @param text Text of the button
     */
    public OptionReturnButton(Vector2D pos, String text) {
        super(pos, text);
    }

    @Override
    public void performAction() {
        SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(true);
        SwingUtilities.getWindowAncestor(this).dispose();
    }
    
}
