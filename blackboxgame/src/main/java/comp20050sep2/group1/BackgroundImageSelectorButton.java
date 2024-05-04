package comp20050sep2.group1;

import java.io.File;

import javax.swing.*;

import comp20050sep2.group1.utils.Vector2D;

public class BackgroundImageSelectorButton extends AbstractButton{

    /**
     * @param pos Position of the background image selector button
     * @param text Text of the button
     */
    public BackgroundImageSelectorButton(Vector2D pos, String text) {
        super(pos, text);
    }

    @Override
    public void performAction() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int response = fileChooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            System.out.println(file);
            GamePanel.changeBackground(file);
        }
    }
    
}
