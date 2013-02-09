package simulation;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

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
    private Environment myEnvironment;
    private Mass myCenterMass;
    private List<Environment> myEnvironmentForces;

    /**
     * Create a game of the given size with the given display for its shapes.
     */
    public Model (Canvas canvas) {
        myView = canvas;
        myMasses = new ArrayList<Mass>();
        mySprings = new ArrayList<Spring>();
        myEnvironmentForces = new ArrayList<Environment>();
    }
    
//    /**
//     * Sets the gravity vector and viscosity scaling factor for 
//     * each mass according to its value. values are taken from 
//     * environment information
//     * it creates a new vector each time to prevent pointers referring
//     * to the same vector
//     */
//    public void initMassParameters(){
//    	Vector gravity;
//    	for(Mass m : myMasses){
//    		gravity = new Vector(myEnvironment.getGravity());
//    		gravity.scale(m.getMass());
//    		m.setGravity(gravity);
//    	}
//    }
    
    /**
     * sets the current bounds of the view to Environment so subclasses
     * can be aware of the new dimensions of the canvas
     */
    public void updateBounds(){
    	Environment.updateBounds(myView.getSize());
//    	for(Environment f : myEnvironmentForces){
//    		if (f instanceof WallRepulsionForce){
//    			f = (WallRepulsionForce) f; 
//    			f.setBounds(myView.getSize());
//    		}
//    	}
    }

    /**
     * Draw all elements of the simulation.
     */
    public void paint (Graphics2D pen) {
        for (Spring s : mySprings) {
            s.paint(pen);
        }
        if(myCenterMass != null){
        	myCenterMass.paint(pen);
        }
        for (Mass m : myMasses) {
        	m.paint(pen);
        }
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
        //myCenterMass = myEnvironment.applyForces(myMasses, bounds);
        for (Mass m : myMasses) {
            m.update(elapsedTime, bounds);
        }
    }

    /**
     * Add given mass to this simulation.
     */
    public void add (Mass mass) {
        myMasses.add(mass);
    }
    
    /**
     * Add given force to this simulation.
     */
    public void add (Environment force) {
        myEnvironmentForces.add(force);
    }
    
    /**
     * retrieves environment
     */
    public Environment getEnvironment(){
    	return myEnvironment;
    }

    /**
     * Add given spring to this simulation.
     */
    public void add (Spring spring) {
        mySprings.add(spring);
    }
}
