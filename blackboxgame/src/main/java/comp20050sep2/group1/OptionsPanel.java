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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.tools.javac.Main;
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

    public JSlider gridSize;
    public JSlider atomCount;

    public JSlider soundSlider;
    public boolean muted = false;

    public int gridSizeVar = 5;
    public int atomCountVar = 6;

    String text = "Options Panel";

    BackgroundImageSelectorButton bgButton;
    OptionReturnButton returnButton;
    OptionMuteButton muteButton;

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

        this.bgButton = new BackgroundImageSelectorButton(new Vector2D(screenWidth/4.0 - 150, screenHeight/4.0 + 150), "Change Background");
        this.bgButton.addMouseListener(this);
        this.add(bgButton);

        this.returnButton = new OptionReturnButton(new Vector2D(screenWidth/2.0 - 260, screenHeight/4.0 + 200), "Return to main menu");
        this.returnButton.addMouseListener(this);
        this.add(returnButton);

        this.muteButton = new OptionMuteButton(new Vector2D(screenWidth/2.0 - 100, screenHeight/4.0 + 100), "Mute audio");
        this.muteButton.addMouseListener(this);
        this.add(muteButton);

        this.soundSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        this.soundSlider.setBounds(400, 200, 200, 50);

        soundSlider.setOpaque(false);
        soundSlider.setMajorTickSpacing(10);
        soundSlider.setPaintTicks(true);
        soundSlider.setSnapToTicks(true);
        soundSlider.addChangeListener(new SliderListener());

        this.gridSize = new JSlider(JSlider.HORIZONTAL, 3, 8, 5);
        
        this.gridSize.setBounds(150, 200, 200, 50);

        gridSize.setOpaque(false);
        gridSize.setMajorTickSpacing(1);
        gridSize.setPaintTicks(true);
        gridSize.setSnapToTicks(true);
        gridSize.addChangeListener(new GridListener());

        this.atomCount = new JSlider(JSlider.HORIZONTAL, 3, 20, 6);
        this.atomCount.setBounds(150, 260, 200, 50);

        atomCount.setOpaque(false);
        atomCount.setMajorTickSpacing(1);
        atomCount.setPaintTicks(true);
        atomCount.setSnapToTicks(true);
        atomCount.addChangeListener(new atomListener());

        this.add(gridSize);
        this.add(atomCount);

        this.add(soundSlider);

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



        Font ogFont = g.getFont();
        
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 17));
        g.drawString("Grid size", gridSize.getBounds().x - 100, gridSize.getBounds().y + 35);
        g.drawString("Atom count", gridSize.getBounds().x - 100, gridSize.getBounds().y + 95);
        g.drawString("Volume", soundSlider.getBounds().x + 70, soundSlider.getBounds().y + 60);

        g.drawString(gridSize.getValue() + "", gridSize.getBounds().x + 80, gridSize.getBounds().y - 5);
        g.drawString(atomCount.getValue() + "", atomCount.getBounds().x + 80, atomCount.getBounds().y - 5);

        g.setFont(ogFont);

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
        else if(e.getSource() == returnButton){
            returnButton.performAction();
        }
        else if (e.getSource() == muteButton) {
            muteButton.performAction();
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

    private class SliderListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent c) {
            MainMenuPanel.get().updateBGMVolume();
        }
    }

    private class GridListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent c){
            MainMenuPanel.get().updateGridSize(gridSize.getValue());
            OptionsPanel.get().repaint();
        }
    }

    private class atomListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent c){
            MainMenuPanel.get().updateAtomCount(atomCount.getValue());
            OptionsPanel.get().repaint();
        }
    }
    
}
