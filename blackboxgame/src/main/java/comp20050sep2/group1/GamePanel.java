package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    private static GamePanel INSTANCE;
    public static GamePanel get() {
        if (INSTANCE == null)
            INSTANCE = new GamePanel();
        return INSTANCE;
    }

    public Graphics2D graphics;
    
    //Screen settings
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;

    final int FPS = 60;

    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;

    HexBoard board;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();


        board = new HexBoard(60, 500, 300, 3);
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
        draw();
        this.graphics = null;
    }

    public void draw(){
        board.draw();
    }

}
