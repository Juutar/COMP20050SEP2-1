package comp20050sep2.group1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    //Screen settings
    final int originalTileSize = 16;    //16 x 16 tiles
    final int scale = 5;

    final int FPS = 60;

    public final int tileSize = originalTileSize * scale;  //48
    final int maxScreenCol = 16;
    final int maXScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maXScreenRow;

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
        draw(g);

    }

    public void draw(Graphics g){
        
        int size = 50;
        int x = 640;
        int y = 480 - 50;

        Hexagon h = new Hexagon((Graphics2D)g, size, x, y);
        h.drawHexagon();

        h = new Hexagon((Graphics2D)g, size, x + 100 * Math.cos(Math.toRadians(30)), y);
        h.drawHexagon();
        h = new Hexagon((Graphics2D)g, size, x - 100 * Math.cos(Math.toRadians(30)), y);
        h.drawHexagon();

        h = new Hexagon((Graphics2D)g, size, x + 43.5, y + 150 * Math.sin(Math.toRadians(30)));
        h.drawHexagon();
        h = new Hexagon((Graphics2D)g, size, x - 43.5, y + 150 * Math.sin(Math.toRadians(30)));
        h.drawHexagon();

        h = new Hexagon((Graphics2D)g, size, x + 43.5, y - 150 * Math.sin(Math.toRadians(30)));
        h.drawHexagon();
        h = new Hexagon((Graphics2D)g, size, x - 43.5, y - 150 * Math.sin(Math.toRadians(30)));
        h.drawHexagon();


        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 2 * (6 * i); j++){
                
            }
        }

        

    }

}
