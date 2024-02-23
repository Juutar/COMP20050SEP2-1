package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;
import org.junit.Test;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import static org.junit.Assert.*;

/* TODO Defect Testing ?*/
/* TODO Remove Useless Classes*/
public class Sprint1Tests
{
    /*
    * Depend on interfaces and not concrete volatile implementation
    * Hide app structure from tests
    * Keep tests independents
    * minimize asserts (1 concept != 1 theme)
    * */

    /*
    * Unit testing: modules individually
    * Integration testing: major subsystems
    * Performance testing: computation and rendering delays
    * Regression testing: modified code didn't break previous features
     */

    @Test
    public void testHexagonBoard() {
        GamePanel.get().startGameThread();
        int size = GamePanel.get().board.getSize();
        assertEquals(1 + (6L *size*(size+1) / 2), GamePanel.get().board.getNumHexes());
    }

    @Test
    public void testGuessAtoms() {
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
    public void testTrueAtoms() {
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

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        Hexagon hexagon = GamePanel.get().board.guessAtomHexagons[0];
        assertNotNull(hexagon);
        assertNotNull(hexagon.guessAtom);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 2, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        assertNull(GamePanel.get().board.guessAtomHexagons[0]);
        assertNull(hexagon.guessAtom);
    }

    @Test
    public void testAtomToggle_ThreeAtoms() {
        GamePanel.get().startGameThread();
        assertNull(GamePanel.get().board.guessAtomHexagons[2]);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 2, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 3, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight, 1, false));

        Hexagon hexagon = GamePanel.get().board.guessAtomHexagons[2];
        assertNotNull(hexagon);
        assertNotNull(hexagon.guessAtom);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 4, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 5, 0, GamePanel.get().screenWidth, GamePanel.get().screenHeight/2, 1, false));
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 6, 0, GamePanel.get().screenWidth/2, GamePanel.get().screenHeight, 1, false));

        assertNull(GamePanel.get().board.guessAtomHexagons[2]);
        assertNull(hexagon.guessAtom);
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
    public void testRandomizedAtoms() {
        GamePanel.get().startGameThread();
        assertEquals(6, GamePanel.get().board.guessAtomHexagons.length);
        assertNotNull(GamePanel.get().board.trueAtomHexagons[0]);
        assertNotNull(GamePanel.get().board.trueAtomHexagons[5]);
        Hexagon[] firstGameRandomAtoms = GamePanel.get().board.trueAtomHexagons;
        GamePanel.get().gameThread.interrupt();

        GamePanel.get().startGameThread();
        assertNotNull(GamePanel.get().board.trueAtomHexagons[0]);
        assertNotNull(GamePanel.get().board.trueAtomHexagons[5]);
        Hexagon[] secondGameRandomAtoms = GamePanel.get().board.trueAtomHexagons;

        assertFalse(Arrays.equals(firstGameRandomAtoms, secondGameRandomAtoms));

    }

    @Test
    public void testAtomPropertiesCorrect() {
        GamePanel.get().startGameThread();
        Hexagon hexagon = GamePanel.get().board.trueAtomHexagons[0];

        assertFalse(hexagon.isCorrect());
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, (int) hexagon.pos.x, (int) hexagon.pos.y, 1, false));
        assertTrue(hexagon.isCorrect());
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, (int) hexagon.pos.x, (int) hexagon.pos.y, 1, false));
        assertFalse(hexagon.isCorrect());
    }

    @Test
    public void testAtomPropertiesIncorrect() {
        GamePanel.get().startGameThread();
        Hexagon hexagon = GamePanel.get().board.trueAtomHexagons[0];

        assertTrue(hexagon.isIncorrect());
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, (int) hexagon.pos.x, (int) hexagon.pos.y, 1, false));
        assertFalse(hexagon.isIncorrect());
        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, (int) hexagon.pos.x, (int) hexagon.pos.y, 1, false));
        assertTrue(hexagon.isIncorrect());
    }

    @Test
    public void testAtomPropertiesNeutral() {
        GamePanel.get().startGameThread();
        Hexagon hexagon = null;
        for (Hexagon h : GamePanel.get().board.getHexes()) {
            if (!Arrays.asList(GamePanel.get().board.trueAtomHexagons).contains(h)) {
                hexagon = h;
                break;
            }
        }

        assertNotNull(hexagon);
        assertFalse(hexagon.isCorrect());
        assertFalse(hexagon.isIncorrect());
    }


    @Test
    public void testCirclesInfluence() {
        Atom atom = new Atom(new Vector2D(0, 0), 5, false);
        assertTrue(atom.circleInfluence.pos.equals(atom.pos));
        assertTrue(atom.circleInfluence.radius > atom.radius);
        assertEquals(atom.circleInfluence.color, atom.color);

        Atom guessAtom = new Atom(new Vector2D(0, 0), 5, true);
        assertEquals(atom.circleInfluence.color, atom.color);
    }

    @Test
    public void testLabels() {
        GamePanel.get().startGameThread();
        int numSides = 0;
        for(Hexagon h : GamePanel.get().board.getHexes()) {
            if(h.pointableSides == 0) {
                assertNull(h.boardLabels);
            } else {
                assertEquals(h.pointableSides, h.boardLabels.length);
                numSides += h.pointableSides;
            }
        }
        assertEquals(42, numSides);
    }

    @Test
    public void testRayPointers() {
        GamePanel.get().startGameThread();
        Vector2D pos = new Vector2D(GamePanel.get().screenWidth, GamePanel.get().screenHeight/2.0);
        Hexagon hexagon = GamePanel.get().board.closestHexToCoords(pos);
        BoardLabel label = GamePanel.get().board.closestLabelToCoords(pos); //function used to replace labels with ray pointers
        assertNotNull(hexagon.boardLabels);
        assertTrue(Arrays.asList(hexagon.boardLabels).contains(label));
    }

    @Test
    public void testAtomButton() {
        GamePanel.get().startGameThread();
        ShowAtomButton button = GamePanel.get().showAtomButton;

        assertTrue(button.isStateShow());
        assertFalse(GamePanel.get().board.trueAtomsVisible);

        button.performAction();
        assertFalse(button.isStateShow());
        assertTrue(GamePanel.get().board.trueAtomsVisible);

        button.performAction();
        assertTrue(button.isStateShow());
        assertFalse(GamePanel.get().board.trueAtomsVisible);
    }


    @Test
    public void testRayButton() {
        GamePanel.get().startGameThread();
        ShowRayButton button = GamePanel.get().showRayButton;

        assertTrue(button.isStateShow());
        assertTrue(GamePanel.get().board.atomSelectorOn);

        button.performAction();
        assertFalse(button.isStateShow());
        assertFalse(GamePanel.get().board.atomSelectorOn);

        GamePanel.get().mouseClicked(new MouseEvent(GamePanel.get(), MouseEvent.MOUSE_CLICKED, 1, 0, (int) GamePanel.get().screenWidth/2, (int) GamePanel.get().screenHeight/2, 1, false));
        assertNull(GamePanel.get().board.guessAtomHexagons[0]);

        button.performAction();
        assertTrue(button.isStateShow());
        assertTrue(GamePanel.get().board.atomSelectorOn);

    }
}
