package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, MouseListener{

    private static GamePanel INSTANCE;
    public static GamePanel get() {
        if (INSTANCE == null)
            INSTANCE = new GamePanel();
        return INSTANCE;
    }

    public Graphics2D graphics;

    public Vector2D mouseCoords = new Vector2D(0, 0);
    
    //Screen settings
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;

    final int FPS = 60;

    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;

    Vector2D lastSize;

    HexBoard board;
    ShowAtomButton showButton;
    ShowRayButton showRayButton;

    KeyHandler keyH = new KeyHandler();
    MouseMoveHandler mouseMoveHandler = new MouseMoveHandler();
    Thread gameThread;

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
    }


    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

        Vector2D viewport = new Vector2D(GamePanel.get().getSize().width, GamePanel.get().getSize().height);

        board = new HexBoard(60, viewport.mul(0.5), 3, 6);

        showButton = new ShowAtomButton(new Vector2D(screenWidth/2.0, screenHeight/10.0));
        showButton.addMouseListener(this);
        this.add(showButton);

        showRayButton = new ShowRayButton(new Vector2D(screenWidth/2.0, screenHeight - screenHeight/10.0));
        showRayButton.addMouseListener(this);
        this.add(showRayButton);

    }
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta > 1){
                update();
                repaint();
                delta --;
                drawCount ++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update(){
        
        

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.graphics = (Graphics2D)g;

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        draw();
        this.graphics = null;
    }

    private ImageIcon backgroundImage;
    private boolean imageFailed = false;
    private void drawBackgroundImage() {
        if (imageFailed)
            return;

        if (backgroundImage == null) {
            try {
                backgroundImage = new ImageIcon(this.getClass().getResource("/bg.jpg"));
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

        g.drawImage(backgroundImage.getImage(), (int)-toLeft, 0, (int)(imageSize.x * scale), (int)viewport.y, null);
    }

    public void draw(){
        // check for resize
        Vector2D viewport = new Vector2D(GamePanel.get().getSize().width, GamePanel.get().getSize().height);
        if (!viewport.equals(lastSize)) {
            board.reposition(viewport.mul(0.5));
        }

        drawBackgroundImage();
        board.draw();

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == showButton) {
            showButton.toggleState();
            for(Hexagon hex : board.trueAtomHexagons) {
                hex.toggleTrue();
            }
            board.toggleEvaluate();
        }
        else if(e.getSource() == showRayButton){
            board.toggleAtomSelector = !board.toggleAtomSelector;
        }
        else if(board.toggleAtomSelector){
            Vector2D vec = new Vector2D(e.getX(), e.getY());
            if (board.closestHexToCoords(vec).hasGuessAtom() || board.atomIndex < board.numAtoms){
                if (board.closestHexToCoords(vec).toggleGuess()) {
                    board.guessAtomHexagons[board.atomIndex++] = board.closestHexToCoords(vec);
                } else {
                    board.guessAtomHexagons[--board.atomIndex] = null;
                }
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
