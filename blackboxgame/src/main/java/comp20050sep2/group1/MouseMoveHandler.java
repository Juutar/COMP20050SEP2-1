package comp20050sep2.group1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMoveHandler implements MouseMotionListener {
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        GamePanel.get().mouseCoords.x = mouseEvent.getX();
        GamePanel.get().mouseCoords.y = mouseEvent.getY();
    }
}
