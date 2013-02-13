package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.List;
import util.Text;
import view.Canvas;


/**
 * Class created to handle the inputs of the game.
 * 
 * @author Henrique Moraes, Thomas Varner
 * 
 */
public class Control {
    private static final int RATE_OF_RESIZE = 2;
    private static final int MINIMUM_VIEW_HEIGHT = 100;
    private static final int PIXELS_PER_RESIZE = 10;
    private static final int COLOR_TONE_INCREMENT = 8;
    private static final int MAX_COLOR_TONE = 255;
    private static final int KEY_DELAY = 6;
    

    private int keyCounter = 0;
    private Canvas myView;

    private int pixelsToResize = 0;
    private int colorCounter = 0;
    private String myLabelDescription = "";
    private String myLastToggleStatus = "";
    private Text myStatus = new Text("");

    public Control (Canvas canvas) {
        myView = canvas;
    }

    /**
     * Paints the status of inputs to notify the user
     * 
     * @param pen Graphics of the view
     */
    public void paint (Graphics2D pen) {
        if (colorCounter > MAX_COLOR_TONE) {
            colorCounter = 0;
            myLabelDescription = myLastToggleStatus = "";

        }
        if (myLabelDescription.length() != 0) {
            myStatus.setText(myLabelDescription + " " + myLastToggleStatus);
            myStatus.paint(pen, new Point(myView.getWidth() / 2, myView.getHeight() / 2),
                           new Color(colorCounter, colorCounter, colorCounter));
            colorCounter += COLOR_TONE_INCREMENT;
        }
    }

    /**
     * Updates the simulation's state based on inputs
     */
    public void update (List<Environment> environmentForces) {
        keyCounter++;
        checkInputs(environmentForces);
        checkResize(myView.getSize());
    }

    /**
     * Checks whether a key was pressed and apply its effects
     */
    private void checkInputs (List<Environment> environmentForces) {

        if (keyCounter < KEY_DELAY) { return; }

        int key = myView.getLastKeyPressed();

        Environment toggledForce = null;

        for (Environment f : environmentForces) {
            toggledForce = f.toggleForce(key);
            if (toggledForce != null) {
                myLabelDescription = toggledForce.getName();
                myLastToggleStatus = toggledForce.isOnOff();
                keyCounter = 0;
                colorCounter = 0;
                break;
            }
        }
        checkResizeInputs(key);
        checkAssemblyHandling(key);
    }

    private void checkAssemblyHandling (int key) {
        if (key == KeyEvent.VK_N) {
            myView.loadModel();
        }
        else if (key == KeyEvent.VK_C) {
            myLabelDescription = "Assemblies Cleared";
            myView.clear();
        }
    }

    private void checkResizeInputs (int key) {
        if (key == KeyEvent.VK_UP) {
            pixelsToResize = PIXELS_PER_RESIZE;
        }
        else if (key == KeyEvent.VK_DOWN) {
            pixelsToResize = -PIXELS_PER_RESIZE;
        }
    }

    /**
     * Checks if the view has to be resized
     */
    private void checkResize (Dimension bounds) {
        if (bounds.height < MINIMUM_VIEW_HEIGHT && pixelsToResize < 0) {
            pixelsToResize = 0;
            return;
        }
        if (pixelsToResize > 0) {
            myView.setSize(bounds.width + 1, bounds.height + 1);
            pixelsToResize -= RATE_OF_RESIZE;
        }
        else if (pixelsToResize < 0) {
            myView.setSize(bounds.width - 1, bounds.height - 1);
            pixelsToResize += RATE_OF_RESIZE;
        }
    }
}
