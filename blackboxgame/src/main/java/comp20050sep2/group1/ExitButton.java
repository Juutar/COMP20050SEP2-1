package comp20050sep2.group1;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import comp20050sep2.group1.utils.Vector2D;

public class ExitButton extends AbstractButton{

    public ExitButton(Vector2D pos, String text) {
        super(pos, text);
    }

    @Override
    public void performAction() {
        
        Window window = SwingUtilities.getWindowAncestor(this);

        if(window instanceof JFrame){
            JFrame frame = (JFrame) window;
            frame.dispose(); // Close the JFrame
        }

        System.exit(0);
    }
    
}
