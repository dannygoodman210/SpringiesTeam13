package simulation;

import util.Vector;


/**
 * Represents a mass that does not move on the screen regardless of
 * the forces applied
 * 
 * @author Henrique Moraes
 * 
 */
public class FixedMass extends Mass {
    public static final Vector NO_ACCELERATION = new Vector(0, 0);

    /**
     * Constructor for this class
     */
    public FixedMass (double x, double y, double mass) {
        super(x, y, -mass);

    }

    /**
     * Use the given force to change this mass's acceleration.
     * For a fixed mass, it does not add any force
     */
    @Override
    public void applyForce (Vector force) {
        // does not do anything because mass is fixed
    }

}
