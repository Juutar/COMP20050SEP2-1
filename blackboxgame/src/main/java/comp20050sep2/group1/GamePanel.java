package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GamePanel extends JPanel implements Runnable, MouseListener {

    private static GamePanel INSTANCE;
    //Screen settings
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;
    final int FPS = 60;
    public Graphics2D graphics;
    public Vector2D mouseCoords = new Vector2D(0, 0);
    public Vector3D lastMousePoint;
    Vector2D lastSize;
    HexBoard board;
    ShowAtomButton showAtomButton;
    ShowRayButton showRayButton;
    ExitToMenuButton exitToMenuButton;
    OutputBox outputBox;
    KeyHandler keyH = new KeyHandler();
    MouseMoveHandler mouseMoveHandler = new MouseMoveHandler();
    Thread gameThread;
    public static ImageIcon backgroundImage;
    private boolean imageFailed = false;
    public int boardSize;

    /**
     * Constructs the jpanel where the game is played and sets all attributes
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        this.setMinimumSize(new Dimension(10, 10));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseMotionListener(mouseMoveHandler);
        this.setFocusable(true);
        this.lastSize = new Vector2D(screenWidth, screenHeight);
        this.addMouseListener(this);
        this.setLayout(null);
        this.lastMousePoint = new Vector3D();
        this.boardSize = 5;
    }

    /**
     * @return the game panel singleton instance
     */
    public static GamePanel get() {
        if (INSTANCE == null)
            INSTANCE = new GamePanel();
        return INSTANCE;
    }

    /**
     * Destroys it from memory explicitly
     */
    public static void destroy() {
        if (INSTANCE != null) {
            SwingUtilities.getWindowAncestor(INSTANCE).setVisible(false);
            SwingUtilities.getWindowAncestor(INSTANCE).removeAll();
        }
        INSTANCE = null;
    }

    /**
     * Function to start the game thread for this panel
     */
    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

        Vector2D viewport = new Vector2D(GamePanel.get().getSize().width, GamePanel.get().getSize().height);

        board = new HexBoard(40, viewport.mul(0.5), OptionsPanel.get().gridSize.getValue(), OptionsPanel.get().atomCount.getValue());

        showAtomButton = new ShowAtomButton(new Vector2D((screenWidth - 200) / 15.0 + 10, screenHeight - screenHeight / 10.0 + 40));
        showAtomButton.addMouseListener(this);
        this.add(showAtomButton);

        showRayButton = new ShowRayButton(new Vector2D((screenWidth - 200) / 15.0 + 10 + showAtomButton.width + 2, screenHeight - screenHeight / 10.0 + 40));
        showRayButton.addMouseListener(this);
        this.add(showRayButton);

        exitToMenuButton = new ExitToMenuButton(new Vector2D(screenWidth - 10 - exitToMenuButton.width, screenHeight - screenHeight / 10.0 + 40));
        exitToMenuButton.addMouseListener(this);
        this.add(exitToMenuButton);

        outputBox = new OutputBox(new Vector2D(screenWidth/2.0, screenHeight/20.0));

    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta > 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update() {


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = (Graphics2D) g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        draw((Graphics2D) g);

        this.graphics = null;
    }

    /**
     * Function to change background based on file
     * @param file File to be changed to
     */
    public static void changeBackground(File file){
        GamePanel.backgroundImage = new ImageIcon(String.valueOf(file));
    }

    /**
     * Function to draw the image and fit correctly
     */
    private void drawBackgroundImage() {
        if (imageFailed)
            return;

        if (backgroundImage == null) {
            try {
                  backgroundImage = new ImageIcon(this.getClass().getResource("/40012.jpg"));
            } catch (NullPointerException e) {
                System.out.println("background image missing");
                imageFailed = true;
                return;
            }
        }

        Vector2D imageSize = new Vector2D(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        Vector2D viewport = new Vector2D(GamePanel.get().getSize().width, GamePanel.get().getSize().height);

        double scale = 1.0;
        if (imageSize.y != viewport.y)
            scale = viewport.y / imageSize.y;

        double toLeft = (imageSize.x * scale - viewport.x) / 2.0;

        Graphics2D g = GamePanel.get().graphics;

        g.drawImage(backgroundImage.getImage(), (int) -toLeft, 0, (int) (imageSize.x * scale), (int) viewport.y, null);
    }

    /**
     * Draws each frame
     * @param g Object using which to draw on current context
     */
    public void draw(Graphics2D g) {
        // check for resize
        Vector2D viewport = new Vector2D(GamePanel.get().getSize().width, GamePanel.get().getSize().height);
        if (!viewport.equals(lastSize) && board != null) {
            board.reposition(viewport.mul(0.5));
        }

        drawBackgroundImage();

        if (board != null) {
            board.drawBoard();
        }

        if (outputBox != null) {
            outputBox.drawOutputBox(g);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (board == null)
            return;

        if (e.getSource() == showAtomButton) {
            showAtomButton.performAction();
        } else if (e.getSource() == showRayButton) {
            showRayButton.performAction();
        } else if (e.getSource() == exitToMenuButton) {
            exitToMenuButton.performAction();
        } else if (board.atomSelectorOn) {
            Vector2D vec = new Vector2D(e.getX(), e.getY());
            if (board.closestHexToCoords(vec).hasGuessAtom() || board.atomIndex < board.numAtoms) {
                if (board.closestHexToCoords(vec).toggleGuess()) {
                    board.guessAtomHexagons[board.atomIndex++] = board.getHexes().getKey(board.closestHexToCoords(vec));
                } else {
                    board.guessAtomHexagons[--board.atomIndex] = null;
                }

                MainMenuPanel.get().playSound("/atom.wav");
            }
        } else {     //shooting rays
            BoardLabel bl = board.closestLabelToMouseCoords();
            if (!board.rayAlreadyExists(bl)) {
                board.rayList.add(new Ray(bl));
                MainMenuPanel.get().playSound("/boom.wav");
            }
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
