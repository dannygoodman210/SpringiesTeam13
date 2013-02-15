package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * Spring class extends Sprite class. Connects two Mass objects together by a Spring.
 * Applies forces on Mass objects when spring is stretched or compressed from rest length.
 * 
 * @author Robert C. Duvall, Danny Goodman
 */
public class Spring extends Sprite {

    private static final Pixmap DEFUALT_IMAGE = new Pixmap("spring.gif");
    private static final int IMAGE_HEIGHT = 20;
    private Mass myStart;
    private Mass myEnd;
    private double myLength;
    private double myK;

    /**
     * Spring Constructor. Initializes parameters and calls super class Sprite constructor.
     * 
     * @param start : Mass 1 connected by Spring
     * @param end : Mass 2 connected by Spring
     * @param length : Rest length of Spring
     * @param kVal : Stiffness
     */
    public Spring (Mass start, Mass end, double length, double kVal) {
        super(DEFUALT_IMAGE, getCenter(start, end), getSize(start, end));
        myStart = start;
        myEnd = end;
        myLength = length;
        myK = kVal;
    }

    /**
     * Return the end mass of the spring.
     * 
     * @return Mass myEnd. instance variable of Mass 2 connected by Spring
     */
    public Mass getEndMass () {
        return myEnd;
    }

    /**
     * Draws Spring as a Line between two masses with a color dependent on whether
     * Spring is compressed or stretched.
     * 
     * @param pen : Graphics 2D object painting in Canvas class
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(getColor(myStart.distance(myEnd) - myLength));
        pen.drawLine((int) myStart.getX(), (int) myStart.getY(), (int) myEnd.getX(),
                     (int) myEnd.getY());
    }

    /**
     * Updates length of Spring and force that it applies to Mass objects.
     * 
     * @param elapsedTime : Time elapsed since last update
     * @param bounds : Bounds of JFrame window for bouncing purposes
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        double dx = myStart.getX() - myEnd.getX();
        double dy = myStart.getY() - myEnd.getY();
        // apply hooke's law to each attached mass
        Vector force = new Vector(Vector.angleBetween(dx, dy),
                                  myK * (myLength - Vector.distanceBetween(dx, dy)));
        myStart.applyForce(force);
        force.negate();
        myEnd.applyForce(force);
        // update sprite values based on attached masses
        setCenter(getCenter(myStart, myEnd));
        setSize(getSize(myStart, myEnd));
        setVelocity(Vector.angleBetween(dx, dy), 0);
    }

    /**
     * Convenience method. Colors the Spring differently for stretching and compressing.
     * 
     * @param diff : length of Spring minus rest length.
     * @return Color of Spring dependent on spring length.
     */
    protected Color getColor (double diff) {
        if (Vector.fuzzyEquals(diff, 0)) {
            return Color.BLACK;
        }
        else if (diff < 0.0) {
            return Color.BLUE;
        }
        else {
            return Color.RED;
        }
    }

    /**
     * Compute center of this spring
     * 
     * @param start : Mass 1 connected to Spring
     * @param end : Mass 2 connected to Spring
     * @return Center of Spring
     */
    protected static Location getCenter (Mass start, Mass end) {
        return new Location((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Compute size of this spring
     * 
     * @param start : Mass 1 connected to Spring
     * @param end : Mass 2 connected to Spring
     * @return Size of Spring
     */
    protected static Dimension getSize (Mass start, Mass end) {
        return new Dimension((int) start.distance(end), IMAGE_HEIGHT);
    }

    protected double getLength () {
        return myLength;
    }

    protected void setLength (double length) {
        myLength = length;
    }
}
