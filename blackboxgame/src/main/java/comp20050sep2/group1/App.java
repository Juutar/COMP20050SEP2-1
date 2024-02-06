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
        
        double dFrames = 0;     //delta frame
        double dUpdates = 0;    //delta updates

        long timeNow = 0;       //variable to store latest time
        long lastTime = System.nanoTime();  //variable to store last time

        //Delta accumulator clock
        while(true){

            timeNow = System.nanoTime();    //get latest time

            dFrames += (timeNow - lastTime) / frameFreq;    //get remaining time for new frame
            dUpdates += (timeNow - lastTime) / upsFreq;     //get remaining time for update
            lastTime = timeNow;

            if(dUpdates >= 1){  //update when neccessary
                //update()
                dUpdates --;
            }

            if(dFrames >= 1){   //repaint when neccessary
                //repaint()
                dFrames--;
            }

        }

    }
}
