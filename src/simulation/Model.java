package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import view.Canvas;


/**
 * Model class. Contains all assemblies and their various
 * masses and springs.
 * 
 * @author Henrique Moraes, Danny Goodman, Thomas Varner
 */
public class Model {
    // constants for mouse dragging
    private static final double DEFAULT_MASS = 10;
    private static final double DEFAULT_KVAL = 0.5;

    // bounds and input for game
    private Canvas myView;

    // for mouse dragging
    private boolean myMouseDragging;
    private Spring myMouseSpring;

    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private List<Environment> myEnvironmentForces;

    // control environment forces
    private Control myControl;

    /**
     * Create a game of the given size with the given display for its shapes.
     * 
     * @param canvas : The view for the Model.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myEnvironmentForces = new ArrayList<Environment>();
        myControl = new Control(myView);
        myMouseDragging = false;
    }

    /**
     * sets the current bounds of the view to Environment so subclasses
     * can be aware of the new dimensions of the canvas
     */
    public void updateBounds () {
        Environment.updateBounds(myView.getSize());
    }

    /**
     * Draw all elements of the simulation.
     * 
     * @param pen : Graphics 2D object painting in Canvas class.
     */
    public void paint (Graphics2D pen) {
        for (Spring s : mySprings) {
            s.paint(pen);
        }

        for (Mass m : myMasses) {
            m.paint(pen);
        }

        myControl.paint(pen);
    }

    /**
     * Update simulation for this moment, given the time since the last moment.
     * 
     * @param elapsedTime : time since last update
     */
    public void update (double elapsedTime) {
        Dimension bounds = myView.getSize();
        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Environment f : myEnvironmentForces) {
            f.applyForce(myMasses);
        }
        for (Mass m : myMasses) {
            m.update(elapsedTime, bounds);
        }

        if (myView.getMousePressed() && !myMouseDragging) {
            createMouseSpring();
            myMouseDragging = true;
        }
        if (myMouseDragging) {
            if (!myView.getMousePressed()) {
                myMouseDragging = false;
                mySprings.remove(myMouseSpring);
            }
            updateDrag(myView.getLastMousePosition());
        }

        myControl.update(myEnvironmentForces);
    }

    /**
     * Add given mass to this simulation.
     * 
     * @param mass : new mass to be added to Model.
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }

    /**
     * Add given force to the list of this simulation and make
     * an individual reference to it
     * 
     * @param force : one of the four forces that extend Environment
     */
    public void add (Environment force) {
        myEnvironmentForces.add(force);
    }

    /**
     * Add given spring to this simulation.
     * 
     * @param spring : new spring to be added to the Model.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }

    /**
     * Clear all masses and springs from Model.
     */
    public void clear () {
        myMasses.clear();
        mySprings.clear();
    }

    // Four private methods for Mouse Dragging
    private void createMouseSpring () {
        Point mouseLocation = myView.getLastMousePosition();
        Mass mass = findClosestMass(mouseLocation);
        myMouseSpring = new Spring(mass,
                                   new Mass(myView.getLastMousePosition(), DEFAULT_MASS),
                                   getDistanceFromMass(mass, mouseLocation), DEFAULT_KVAL);
        mySprings.add(myMouseSpring);
    }

    private double getDistanceFromMass (Mass mass, Point mouseLocation) {
        return mouseLocation.distance(mass.getX(), mass.getY());
    }

    private Mass findClosestMass (Point mouseLocation) {
        Mass closestMass = myMasses.get(0);
        for (Mass m : myMasses) {
            if (getDistanceFromMass(m, mouseLocation) < 
                    getDistanceFromMass(closestMass, mouseLocation)) {
                closestMass = m;
            }
        }
        return closestMass;
    }

    private void updateDrag (Point mouseLocation) {
        myMouseSpring.getEndMass().setCenter(
                                             mouseLocation.getX(), mouseLocation.getY());
    }
}
