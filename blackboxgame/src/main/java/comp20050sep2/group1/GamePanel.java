package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import comp20050sep2.group1.utils.Vector2D;
import comp20050sep2.group1.utils.Vector3D;

public class GamePanel extends JPanel implements Runnable, MouseListener{

    private static GamePanel INSTANCE;
    public static GamePanel get() {
        if (INSTANCE == null)
            INSTANCE = new GamePanel();
        return INSTANCE;
    }

    public Graphics2D graphics;

    public Vector2D mouseCoords = new Vector2D(0, 0);
    public Vector3D lastMousePoint;
    
    //Screen settings
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;

    final int FPS = 60;

    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;

    public Ray r;
    public ArrayList<Ray> rayList;

    Vector2D lastSize;

    HexBoard board;
    ShowAtomButton showAtomButton;
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
        this.lastMousePoint = new Vector3D();
        this.rayList = new ArrayList<>();
    }


    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

        Vector2D viewport = new Vector2D(GamePanel.get().getSize().width, GamePanel.get().getSize().height);

        board = new HexBoard(50, viewport.mul(0.5), 4, 6);

        showAtomButton = new ShowAtomButton(new Vector2D((screenWidth - 200)/15.0 + 10, screenHeight - screenHeight/10.0 + 40));
        showAtomButton.addMouseListener(this);
        this.add(showAtomButton);

        showRayButton = new ShowRayButton(new Vector2D((screenWidth - 200)/15.0 + 10 + showAtomButton.width + 2, screenHeight - screenHeight/10.0 + 40));
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
        if (!viewport.equals(lastSize) && board != null) {
            board.reposition(viewport.mul(0.5));
        }

        drawBackgroundImage();

        if(board != null){
            board.drawBoard();
            for(Ray r : rayList){
                r.drawRay();
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == showAtomButton) {
            showAtomButton.performAction();
        }
        else if(e.getSource() == showRayButton){
            showRayButton.performAction();
        }
        else if(board.atomSelectorOn){
            Vector2D vec = new Vector2D(e.getX(), e.getY());
            if (board.closestHexToCoords(vec).hasGuessAtom() || board.atomIndex < board.numAtoms){
                if (board.closestHexToCoords(vec).toggleGuess()) {
                    board.guessAtomHexagons[board.atomIndex++] = board.getHexes().getKey(board.closestHexToCoords(vec));
                } else {
                    board.guessAtomHexagons[--board.atomIndex] = null;
                }
            }
        }
        else if(!board.atomSelectorOn){     //shooting rays

            Vector3D oldVel;

            Hexagon lastHex = board.closestLabelToMouseCoords().hexagon;
            Ray r = new Ray(lastHex);
            
            Vector3D vel = Vector3D.addInv(GamePanel.get().lastMousePoint);
            oldVel = vel.copy();

            if(lastHex.hasTrueAtom()){
                System.out.println("atom on edge hit");
                return;
            }

            if(lastHex.underInfluence){
                vel.sum(lastHex.influenceVector);
            }

            if(vel.q == 0 && vel.r == 0 && vel.s == 0){     //when this happens an atom has def been hit
                System.out.println("Atom hit!");
                
            }

            if((vel.q + vel.r + vel.s) != 0){
                vel = lastHex.influenceVector.copy();
            }

            rayList.add(r);
            rayList.get(rayList.size() - 1).setNext(vel);
            

            for(int i = 0; i < board.getHexes().size(); i ++){
                
                oldVel = vel.copy();

                if(rayList.get(rayList.size() - 1).points.get(rayList.get(rayList.size() - 1).points.size() - 1).underInfluence){
                    vel.sum(rayList.get(rayList.size() - 1).points.get(rayList.get(rayList.size() - 1).points.size() - 1).influenceVector);
                    if(vel.q == 0 && vel.r == 0 && vel.s == 0){
                        System.out.println("180 rotation should happen");
                        if(board.getHexes().getValue(Vector3D.binaryAdd(board.getHexes().getKey(rayList.get(rayList.size() - 1).points.get(rayList.get(rayList.size() - 1).points.size() - 1)), oldVel)).hasTrueAtom()){

                            System.out.println("atom hit!");
                        }
                        else{
                            vel = Vector3D.addInv(oldVel);
                            System.out.println("rotated no atom hit!");
                        }
                    }
                }

                rayList.get(rayList.size() - 1).setNext(vel);
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
