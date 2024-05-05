package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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

    Clip bgm;

    public int maxScore = -1;

    /**
     * Constructs the main menu jpanel
     */
    public MainMenuPanel() {
        this.setPreferredSize(new Dimension(screenWidth/2, screenHeight/2));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.setMinimumSize(new Dimension(10, 10));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(this);
        this.setLayout(null);

        outputBox = new OutputBox(new Vector2D(screenWidth/4.0, screenHeight/5.0));
        outputBox.setText("No score so far");

        playButton = new PlayButton(new Vector2D(screenWidth/4.0, screenHeight/3.0));
        playButton.addMouseListener(this);
        this.add(playButton);

        optionsButton = new OptionsButton(new Vector2D(playButton.getPos().x, playButton.getPos().y + OptionsButton.getButtonHeight() + 5));
        optionsButton.addMouseListener(this);
        this.add(optionsButton);

        exitButton = new ExitButton(new Vector2D(optionsButton.getPos().x, optionsButton.getPos().y + OptionsButton.getButtonHeight() + 10), "Exit game");
        exitButton.addMouseListener(this);
        this.add(exitButton);

        try {
            this.bgm = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/bgm.wav"));
            this.bgm.open(inputStream);
            updateBGMVolume();
            this.bgm.loop(999999);
        } catch (Exception e) {
            ;
        }

    }

    /**
     * Returns main menu singleton
     * @return main menu singleton
     */
    public static MainMenuPanel get() {
        if (INSTANCE == null) {
            INSTANCE = new MainMenuPanel();
        }
        return INSTANCE;
    }

    /**
     * Update the highest score
     * @param score New score
     */
    public void updateScore(int score) {
        maxScore = maxScore < 0 ? score : Math.min(maxScore, score);
        outputBox.setText(String.valueOf(maxScore));
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
        g.drawString(text, this.getWidth()/2 - g.getFontMetrics().stringWidth(text)/2, this.getHeight()/5);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        g.drawString("Best Score:", this.getWidth()/2 - g.getFontMetrics().stringWidth("Best Score:")/2, this.getHeight()/5 + 50);

        outputBox.drawOutputBox((Graphics2D) g);
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
            GamePanel.destroy();
            MainMenuPanel.get().gameFrame = null;
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

    public void updateBGMVolume() {
        FloatControl gainControl = (FloatControl) bgm.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = OptionsPanel.get().muted ? -100000 : OptionsPanel.get().soundSlider.getValue() / (float)OptionsPanel.get().soundSlider.getMaximum() * 35 - 35;
        gainControl.setValue(gain < -34 ? -10000 : gain - 10);
    }

    public void updateGridSize(int newsize){
        OptionsPanel.get().gridSizeVar = newsize;
        MainMenuPanel.get().repaint();
    }

    public void updateAtomCount(int atmCount){
        OptionsPanel.get().atomCountVar = atmCount;
        MainMenuPanel.get().repaint();
    }

    // prolly shouldnt be here but whatever
    /**
     * Playing background music
     * @param path Path to sound file
     */
    public void playSound(String path) {
        if (OptionsPanel.get().muted)
            return;

        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(path));
            clip.open(inputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = OptionsPanel.get().soundSlider.getValue() / (float)OptionsPanel.get().soundSlider.getMaximum() * 35 - 35;
            gainControl.setValue(gain < -34 ? -10000 : gain);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
