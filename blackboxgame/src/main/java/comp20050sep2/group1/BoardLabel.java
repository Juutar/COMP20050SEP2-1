package comp20050sep2.group1;


import comp20050sep2.group1.utils.Vector2D;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class BoardLabel extends JLabel{
    
    private String text;
    Vector2D pos;

    BoardLabel(String text, Vector2D pos){

        this.text = text;
        this.pos = pos;

    }

    public void writeText(){
        GamePanel.get().graphics.setColor(new Color(255, 150, 150));
        GamePanel.get().graphics.setFont(new Font("Arial", Font.TYPE1_FONT, 15));
        GamePanel.get().graphics.drawString(text, (int)pos.x - 10, (int)pos.y + 5);
    }

}
