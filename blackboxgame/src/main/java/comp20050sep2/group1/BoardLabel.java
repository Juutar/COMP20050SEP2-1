package comp20050sep2.group1;


import javax.swing.JLabel;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

public class BoardLabel extends JLabel{
    
    private String text;
    private Vector2D hexCoors;

    BoardLabel(String text, Vector3D coors){

        this.text = text;
        this.hexCoors = GamePanel.get().board.hexCoordsToCenter(coors);

    }

    public void writeText(){
        GamePanel.get().graphics.drawString(text, (int)hexCoors.x, (int)hexCoors.y);
    }

}
