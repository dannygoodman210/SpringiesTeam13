package simulation;

import util.Location;
import util.Vector;

import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Pixmap;
import util.Sprite;

public class FixedMass extends Mass {
	private static final Vector NO_ACCELERATION = new Vector(0,0);
	
	/**
	 * Constructor for this class
	 */
	public FixedMass (double x, double y, double mass) {
        super(x,y,mass);
        
    }
	
    /**
     * sets the gravity vector for this mass
     */
	@Override
    public void setGravity(Vector gravity){
    }
    
    /**
     * Returns the gravity vector of this mass
     */
	@Override
    public Vector getGravity(){
    	return NO_ACCELERATION;
    }
	
	/**
     * In case this has to be painted differently.
     */
    @Override
    public void paint (Graphics2D pen) {
        super.paint(pen);
    }
    
    /**
     * In case this class has to be updated differently than its
     * super class
     */
    @Override 
    public void update (double elapsedTime, Dimension bounds) {
    	super.update(elapsedTime, bounds);
    }
    
    /**
     * Use the given force to change this mass's acceleration.
     */
    @Override
    public void applyForce (Vector force) { 		 
    }
    
    
}
