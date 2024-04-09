package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuPanel extends JPanel implements MouseListener, WindowListener {

    private static MainMenuPanel INSTANCE;
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;
    private final ImageIcon backgroundImage = new ImageIcon(this.getClass().getResource("/RedLaserBeams.jpg"));

    String text = "Blackbox Main Menu";
    OutputBox outputBox;
    PlayButton playButton;
    OptionsButton optionsButton;
    ExitButton exitButton;
    GameFrame gameFrame;

    public MainMenuPanel() {
        this.setPreferredSize(new Dimension(screenWidth/2, screenHeight/2));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.setMinimumSize(new Dimension(10, 10));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.setLayout(null);

        outputBox = new OutputBox(new Vector2D(this.getWidth()/2.0, this.getHeight()/3.0));

        playButton = new PlayButton(new Vector2D(screenWidth/4.0, screenHeight/4.0));
        playButton.addMouseListener(this);
        this.add(playButton);

        optionsButton = new OptionsButton(new Vector2D(playButton.getPos().x, playButton.getPos().y + OptionsButton.getButtonHeight() + 5));
        optionsButton.addMouseListener(this);
        this.add(optionsButton);

        exitButton = new ExitButton(new Vector2D(optionsButton.getPos().x, optionsButton.getPos().y + OptionsButton.getButtonHeight() + 10), "Exit game");
        exitButton.addMouseListener(this);
        this.add(exitButton);

    }

    public static MainMenuPanel get() {
        if (INSTANCE == null) {
            INSTANCE = new MainMenuPanel();
        }
        return INSTANCE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
        g.setColor(Color.WHITE);
        g.drawString(text, this.getWidth()/2 - g.getFontMetrics().stringWidth(text)/2, this.getHeight()/4);

        if (outputBox != null) {
            outputBox.drawOutputBox((Graphics2D) g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == playButton) {
            playButton.performAction();
        }
        else if(e.getSource() == exitButton){
            exitButton.performAction();
        }
        else if(e.getSource() == optionsButton){
            optionsButton.performAction();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        if (e.getSource() == gameFrame) {
            SwingUtilities.getWindowAncestor(MainMenuPanel.get()).setVisible(true);
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
