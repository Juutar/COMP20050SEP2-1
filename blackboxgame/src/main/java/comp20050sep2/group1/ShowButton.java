package comp20050sep2.group1;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShowButton extends JButton {

    boolean showState;

    public ShowButton() {
        setSize(150, 40);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        setFocusable(false);
        setVisible(true);

        this.showState = false;
        toggleState();
    }

    public void toggleState() {
        if(showState) {
            setText("Hide True Atoms");
            setBackground(new Color(0.5F, 0.5F, 0.5F, 0.5F));
            setForeground(Color.black);
            setBorder(new LineBorder(Color.black));
        } else {
            setText("Show True Atoms");
            setBackground(new Color(0.0F, 0.F, 0.F, 0.5F));
            setForeground(Color.white);
            setBorder(new LineBorder(Color.white, 1));
        }
        showState = !showState;
    }
}
