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
        GamePanel.get().startGameThread();
        int size = GamePanel.get().board.getSize();
        assertEquals(1 + (6L *size*(size+1) / 2), GamePanel.get().board.getNumHexes());

    }

    @Test
    public void testGuessAtomRendering() {
        GamePanel.get().startGameThread();
        Hexagon hexagon = new Hexagon(5, new Vector2D(3, 6));
        hexagon.toggleGuess();
        assertNotNull(hexagon.guessAtom);

        Atom atom = hexagon.guessAtom;
        assertEquals(atom.GUESS_COLOR, atom.color);
        assertEquals(hexagon.side, atom.radius, 0.0);
        assertTrue(hexagon.pos.equals(atom.pos));
    }

    @Test
    public void testTrueAtomRendering() {
        GamePanel.get().startGameThread();
        Hexagon hexagon = new Hexagon(5, new Vector2D(3, 6));
        hexagon.placeTrueAtom();
        assertNotNull(hexagon.trueAtom);

        Atom atom = hexagon.trueAtom;
        assertEquals(atom.TRUE_COLOR, atom.color);
        assertEquals(hexagon.side, atom.radius, 0.0);
        assertTrue(hexagon.pos.equals(atom.pos));
    }

    @Test
    public void testAtomToggle_OneAtom() {
        GamePanel.get().startGameThread();
        assertNull(GamePanel.get().board.guessAtomHexagons[0]);
        assertNull(GamePanel.get().board.guessAtomHexagons[0].guessAtom);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        assertNotNull(GamePanel.get().board.guessAtomHexagons[0]);
        assertNotNull(GamePanel.get().board.guessAtomHexagons[0].guessAtom);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 2, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        assertNull(GamePanel.get().board.guessAtomHexagons[0]);
        assertNull(GamePanel.get().board.guessAtomHexagons[0].guessAtom);
    }

    @Test
    public void testAtomToggle_ThreeAtoms() {
        GamePanel.get().startGameThread();
        assertNull(GamePanel.get().board.guessAtomHexagons[2]);
        assertNull(GamePanel.get().board.guessAtomHexagons[2].guessAtom);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 2, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 3, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight, 1, false));

        assertNotNull(GamePanel.get().board.guessAtomHexagons[2]);
        assertNotNull(GamePanel.get().board.guessAtomHexagons[2].guessAtom);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 4, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 5, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 6, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight, 1, false));

        assertNull(GamePanel.get().board.guessAtomHexagons[2]);
        assertNull(GamePanel.get().board.guessAtomHexagons[2].guessAtom);
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
    public void testCirclesInfluenceRendering() {
        Atom atom = new Atom(new Vector2D(0, 0), 5, false);
        assertTrue(atom.circleInfluence.pos.equals(atom.pos));
        assertTrue(atom.circleInfluence.radius > atom.radius);
        assertEquals(atom.circleInfluence.color, atom.color);

        Atom guessAtom = new Atom(new Vector2D(0, 0), 5, true);
        assertEquals(atom.circleInfluence.color, atom.color);
    }

    @Test
    public void testLabelsRendering() {}

    @Test
    public void testRayPointerRendering() {}

}
