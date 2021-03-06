package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * XXX.
 * 
 * @author Henrique Moraes, Thomas Varner, Danny Goodman
 */
public class Mass extends Sprite {
    // reasonable default values
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");

    private double myMass;
    private Vector myAcceleration;

    public Mass (double x, double y, double mass) {
        super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
        myMass = mass;
        myAcceleration = new Vector();
    }

    /**
     *  alternate constructor for given Point
     * @param point Location of the mass
     * @param mass the mass of the ass
     */
    public Mass(Point point, double mass) {
        this(point.x, point.y, mass);
    }

    /**
     * @return mass of this mass
     */
    public double getMass () {
        return myMass;
    }

    /**
     * Returns the acceleration vector of this mass
     */
    public Vector getAcceleration () {
        return myAcceleration;
    }

    @Override
    public void update (double elapsedTime, Dimension bounds) {
        checkBoundaries(bounds);

        applyForce(getBounce(bounds));
        // convert force back into Mover's velocity
        getVelocity().sum(myAcceleration);
        myAcceleration.reset();
        // move mass by velocity
        super.update(elapsedTime, bounds);
    }

    /**
     * checks if mass tries to move out of the screen and keeps it
     * within boundaries. This method is an attempt to minimize the
     * bouncing bug
     */
    private void checkBoundaries (Dimension bounds) {
        double centerY = getCenter().y;
        double centerX = getCenter().x;
        if (getTop() <= 0) {
            setCenter(centerX, DEFAULT_SIZE.getHeight() / 2);
        }

        if (getRight() >= bounds.getWidth()) {
            setCenter(bounds.getWidth() - DEFAULT_SIZE.getWidth() / 2, centerY);
        }

        if (getBottom() >= bounds.getHeight()) {
            setCenter(centerX, bounds.getHeight() - DEFAULT_SIZE.getHeight() / 2);
        }

        if (getLeft() <= 0) {
            setCenter(DEFAULT_SIZE.getWidth() / 2, centerY);
        }
    }

    /**
     * XXX.
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval((int) getLeft(), (int) getTop(), (int) getWidth(), (int) getHeight());
    }

    /**
     * Use the given force to change this mass's acceleration.
     */
    public void applyForce (Vector force) {
        myAcceleration.sum(force);
    }

    /**
     * Convenience method.
     */
    public double distance (Mass other) {
        // this is a little awkward, so hide it
        return new Location(getX(), getY()).distance(new Location(other.getX(), other.getY()));
    }

    // check for move out of bounds
    private Vector getBounce (Dimension bounds) {
        final double IMPULSE_MAGNITUDE = 2;
        Vector impulse = new Vector();
        if (getLeft() <= 0) {
            impulse = new Vector(RIGHT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getRight() >= bounds.width) {
            impulse = new Vector(LEFT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        if (getTop() <= 0) {
            impulse = new Vector(DOWN_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getBottom() >= bounds.height) {
            impulse = new Vector(UP_DIRECTION, IMPULSE_MAGNITUDE);
        }
        impulse.scale(getVelocity().getRelativeMagnitude(impulse));
        return impulse;
    }
}
