package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

import util.Text;
import util.Vector;
import view.Canvas;


/**
 * XXX.
 * 
 * @author Henrique Moraes, Danny Goodman, Thomas Varner
 */
public class Model {
	
    // bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private List<Environment> myEnvironmentForces;
    private Control myControl;

    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myEnvironmentForces = new ArrayList<Environment>();
        myControl = new Control(myView);
    }
    
    /**
     * sets the current bounds of the view to Environment so subclasses
     * can be aware of the new dimensions of the canvas
     */
    public void updateBounds(){
    	Environment.updateBounds(myView.getSize());
    }

    /**
     * Draw all elements of the simulation.
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
     */
    public void update (double elapsedTime) {
        Dimension bounds = myView.getSize();
        for (Spring s : mySprings) {
            s.update(elapsedTime, bounds);
        }
        for (Environment f : myEnvironmentForces){
        	f.applyForce(myMasses);
        }
        for (Mass m : myMasses) {
            m.update(elapsedTime, bounds);
        }
        
        myControl.update();
    }

    /**
     * Add given mass to this simulation.
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }
    
    /**
     * Add given force to the list of this simulation and make 
     * an individual reference to it
     */
    public void add (Environment force) {
        myEnvironmentForces.add(force);
        myControl.add(force);
    }

    /**
     * Add given spring to this simulation.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }
    
}
