package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

import view.Canvas;


/**
 * XXX.
 * 
 * @author Henrique Moraes, Danny Goodman, Thomas Varner
 */
public class Model {
	
    private static final double DEFAULT_MASS = 10;
    private static final double DEFAULT_KVAL = 0.5;
	// bounds and input for game
    private Canvas myView;
    // simulation state
    private List<Mass> myMasses;
    private List<Spring> mySprings;
    private List<Environment> myEnvironmentForces;
    //private Control myControl;
    private boolean mouseDragging;
    private Spring myMouseSpring;

    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myEnvironmentForces = new ArrayList<Environment>();
        //myControl = new Control(myView);
        mouseDragging = false;
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
        
        //myControl.paint(pen);
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
        if (myView.getMousePressed()&&!mouseDragging){
        	createMouseSpring();
        	mouseDragging = true;
        }
        if(mouseDragging){
        	if(!myView.getMousePressed()){
        		mouseDragging = false;
        		mySprings.remove(myMouseSpring);
        	}
        	updateDrag(myView.getLastMousePosition());
        }
        	
        
        //myControl.update();
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
        //myControl.add(force);
    }

    /**
     * Add given spring to this simulation.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }
    
    private void createMouseSpring() {
    	Point mouseLocation = myView.getLastMousePosition();
    	Mass mass = findClosestMass(mouseLocation);
    	myMouseSpring = new Spring(mass,
    			new Mass(myView.getLastMousePosition(),DEFAULT_MASS),
    			massDist(mass,mouseLocation),DEFAULT_KVAL);
    	mySprings.add(myMouseSpring);
    }

	private double massDist(Mass mass, Point mouseLocation) {
		return mouseLocation.distance(mass.getX(),mass.getY());
	}

	private Mass findClosestMass(Point mouseLocation) {
		Mass closeMass = myMasses.get(0);
		for(Mass m:myMasses){
			if(massDist(m,mouseLocation) < massDist(closeMass,mouseLocation))
				closeMass = m;
		}
		return closeMass;
	}
    
	private void updateDrag(Point mouseLocation){
		myMouseSpring.getEndMass().setCenter(
				mouseLocation.getX(), mouseLocation.getY());
	}
}
