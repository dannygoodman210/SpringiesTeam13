package simulation;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.List;
import util.Vector;


/**
 * Class that represents viscosity and its functionalities
 * 
 * @author Henrique Moraes
 * 
 */
public class WallRepulsionForce extends Environment {

    // Directions for vetors
    public static final double DOWN_DIRECTION = 90;
    public static final double LEFT_DIRECTION = 180;
    public static final double UP_DIRECTION = 270;
    public static final double RIGHT_DIRECTION = 0;

    public static final double UPPER_WALL_ID = 1;
    public static final double RIGHT_WALL_ID = 2;
    public static final double BOTTOM_WALL_ID = 3;
    public static final double LEFT_WALL_ID = 4;

    private double myForceDirection;
    private int myID;
    private double myMagnitude;
    private double myExponentialIndex;
    private int myKeyConstant;

    public WallRepulsionForce (int ID, double direction,
                               double magnitude, double exponent) {
        myID = ID;
        myForceDirection = direction;
        myMagnitude = magnitude;
        myExponentialIndex = exponent;
        myName = "Wall " + myID;
        assignKeyConstant();
    }

    /**
     * assigns the respective key constant to this wall according to
     * its ID
     */
    private void assignKeyConstant () {
        if (myID == UPPER_WALL_ID) {
            myKeyConstant = KeyEvent.VK_1;
        }
        else if (myID == RIGHT_WALL_ID) {
            myKeyConstant = KeyEvent.VK_2;
        }
        else if (myID == BOTTOM_WALL_ID) {
            myKeyConstant = KeyEvent.VK_3;
        }
        else if (myID == LEFT_WALL_ID) {
            myKeyConstant = KeyEvent.VK_4;
        }
    }

    /**
     * Calculates and applies wall repulsion force to each mass
     * Does not apply force if isForceOn is off
     */
    public void applyForce (List<Mass> masses) {
        if (!isForceOn) { return; }
        for (Mass m : masses) {
            m.applyForce(calculateWallRepulsion(m));
        }
    }

    /**
     * Retrieve the ID of this wall
     */
    public int getID () {
        return myID;
    }

    /**
     * Calculates the force done by a wall depending on the wall ID.
     */
    private Vector calculateWallRepulsion (Mass m) {
        double distance;
        if (myID == UPPER_WALL_ID) {
            distance = m.getY();
        }
        else if (myID == RIGHT_WALL_ID) {
            distance = myBounds.getWidth() - m.getX();
        }
        else if (myID == BOTTOM_WALL_ID) {
            distance = myBounds.getHeight() - m.getY();
        }
        else {
            distance = m.getX();
        }
        return new Vector(myForceDirection, myMagnitude
                                            / (Math.pow(distance, myExponentialIndex)));
    }

    @Override
    public final Environment toggleForce (final int key) {
        if (myKeyConstant == key) {
            isForceOn = !isForceOn;
            return this;
        }
        return null;
    }
}
