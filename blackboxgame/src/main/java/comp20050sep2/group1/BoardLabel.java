package comp20050sep2.group1;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class BoardLabel extends JLabel{
    
    private String text;

    double x;
    double y;

    BoardLabel(String text){

        this.text = text;

    }

    public void writeText(int xOffset, int yOffset){
        GamePanel.get().graphics.setColor(new Color(255, 150, 150));
        GamePanel.get().graphics.setFont(new Font("Arial", Font.TYPE1_FONT, 15));
        GamePanel.get().graphics.drawString(text, (int)x, (int)y);
    }

}
