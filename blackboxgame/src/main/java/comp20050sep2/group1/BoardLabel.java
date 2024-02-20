package comp20050sep2.group1;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class BoardLabel extends JLabel{
    
    private String text;
    private Hexagon h;

    public Hexagon getH() {
        return h;
    }

    BoardLabel(String text, Hexagon h){

        this.text = text;
        this.h = h;

    }

    public void writeText(int xOffset, int yOffset){
        GamePanel.get().graphics.setColor(new Color(255, 150, 150));
        GamePanel.get().graphics.setFont(new Font("Arial", Font.TYPE1_FONT, 15));
        GamePanel.get().graphics.drawString(text, (int)h.center().x + xOffset, (int)h.center().y + yOffset);
    }

}
