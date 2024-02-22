package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/* TODO Defect Testing ?*/
public class Sprint1Tests
{
    /*
    * Depend on interfaces and not concrete volatile implementation
    * Hide app structure from tests
    * Keep tests independents
    * minimize asserts (1 concept != 1 theme
    * */

    /*
    * Unit testing: modules individually
    * Integration testing: major subsystems
    * Performance testing: computation and rendering delays
    * Regression tesing:
     */
    @Test
    public void testHexagonRendering() {
        //test num hexagons
    }

    @Test
    public void testAtomRendering() {
        //test shape
    }

    @Test
    public void testAtomToggle_OneAtom() {
        GamePanel.get().startGameThread();
        assertNull(GamePanel.get().board.guessAtomHexagons[0]);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        assertNotNull(GamePanel.get().board.guessAtomHexagons[0]);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 2, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        assertNull(GamePanel.get().board.guessAtomHexagons[0]);
    }

    @Test
    public void testAtomToggle_ThreeAtoms() {
        GamePanel.get().startGameThread();
        assertNull(GamePanel.get().board.guessAtomHexagons[0]);
        assertNull(GamePanel.get().board.guessAtomHexagons[1]);
        assertNull(GamePanel.get().board.guessAtomHexagons[3]);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 2, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 3, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight, 1, false));

        assertNotNull(GamePanel.get().board.guessAtomHexagons[0]);
        assertNotNull(GamePanel.get().board.guessAtomHexagons[1]);
        assertNotNull(GamePanel.get().board.guessAtomHexagons[2]);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 4, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 5, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 6, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight, 1, false));

        assertNull(GamePanel.get().board.guessAtomHexagons[0]);
        assertNull(GamePanel.get().board.guessAtomHexagons[1]);
        assertNull(GamePanel.get().board.guessAtomHexagons[2]);
    }

    @Test
    public void testAtomToggle_AboveDefaultMax() {
        GamePanel.get().startGameThread();
        assertEquals(6, GamePanel.get().board.guessAtomHexagons.length);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 2, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 3, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 4, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 5, 0, 0, 0, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 6, 0, 0, GamePanel.get().screenHeight/2, 1, false));

        assertTrue(GamePanel.get().board.closestHexToCoords(new Vector2D(0, GamePanel.get().screenHeight/2.0)).hasGuessAtom());

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 6, 0, GamePanel.get().screenWidth/2, 0, 1, false));

        assertFalse(GamePanel.get().board.closestHexToCoords(new Vector2D(GamePanel.get().screenWidth/2.0, 0)).hasGuessAtom());
    }

    @Test
    public void testAtomProperties() {}

    @Test
    public void testCirclesInfluenceRendering() {}

    @Test
    public void testLabelsRendering() {}

    @Test
    public void testRayPointerRendering() {}

}
