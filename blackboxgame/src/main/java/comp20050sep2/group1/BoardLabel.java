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

        int index = Integer.parseInt(text) - 19;

        if(index % 3 == 0){
            this.h.pointableSides = 3;
        }
        else{
            this.h.pointableSides = 2;
        }

        if(index <= 25){
            //vector = 
            //angle = 120
            //if i % 3 == 0 --> angle +/-= 30
        }
        else if(index <= 27){
            //
        }
        else if(index <= 34){
            
        }
        else if(index <= 36){
            
        }

    }

    public void writeText(int xOffset, int yOffset){
        GamePanel.get().graphics.setColor(new Color(255, 150, 150));
        GamePanel.get().graphics.setFont(new Font("Arial", Font.TYPE1_FONT, 15));
        GamePanel.get().graphics.drawString(text, (int)h.center().x + xOffset, (int)h.center().y + yOffset);
    }

}
