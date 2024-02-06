package comp20050sep2.group1;

/**
 * Hello world!
 *
 */
public class App implements Runnable
{
    
    final int FPS = 120;
    final int UPS = 200;
    final double frameFreq = 1000000000 / FPS;
    final double upsFreq = 1000000000 / UPS;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    @Override
    public void run() {
        
        double dFrames = 0;
        double dUpdates = 0;

        long timeNow = 0;
        long lastTime = System.nanoTime();

        //Delta accumulator clock
        while(true){

            timeNow = System.nanoTime();

            dFrames += (timeNow - lastTime) / frameFreq;
            dUpdates += (timeNow - lastTime) / upsFreq;
            lastTime = timeNow;

            if(dUpdates >= 1){
                //update()
                dUpdates --;
            }

            if(dFrames >= 1){
                //repaint()
                dFrames--;
            }

        }

    }
}
