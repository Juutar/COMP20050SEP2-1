package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class MainMenuPanel extends JPanel implements MouseListener {

    private static MainMenuPanel INSTANCE;
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;
    private final ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/RedLaserBeams.jpg")));

    PlayButton playButton;

    public MainMenuPanel() {
        this.setPreferredSize(new Dimension(screenWidth/2, screenHeight/2));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.setMinimumSize(new Dimension(10, 10));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.setLayout(null);

        playButton = new PlayButton(new Vector2D(this.screenWidth/4.0, this.screenHeight/4.0));
        playButton.addMouseListener(this);
        this.add(playButton);

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
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == playButton) {
            playButton.performAction();
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
}
