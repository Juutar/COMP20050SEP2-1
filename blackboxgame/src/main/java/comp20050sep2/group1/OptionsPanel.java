package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import comp20050sep2.group1.utils.Vector2D;

public class OptionsPanel extends JPanel implements MouseListener, WindowListener{

    private static OptionsPanel INSTANCE;
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;
    private final ImageIcon backgroundImage = new ImageIcon(this.getClass().getResource("/RedLaserBeams.jpg"));



    String text = "Options Panel";
    BackgroundImageSelectorButton bgButton;
    OptionFrame optionFrame;
    
    public OptionsPanel(){
        this.setPreferredSize(new Dimension(screenWidth/2, screenHeight/2));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.setMinimumSize(new Dimension(10, 10));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.setLayout(null);
        this.bgButton = new BackgroundImageSelectorButton(new Vector2D(screenWidth/4.0, screenHeight/4.0), "Change Background");
        this.bgButton.addMouseListener(this);
        this.add(bgButton);

    }
    
    public static OptionsPanel get() {
        if (INSTANCE == null) {
            INSTANCE = new OptionsPanel();
        }
        return INSTANCE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString(text, this.getWidth()/2 - g.getFontMetrics().stringWidth(text)/2, this.getHeight()/4);
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
        
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
        
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
        
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == bgButton){
            bgButton.performAction();
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
       
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        
    }
    
}
