package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Mass extends Sprite {    
    // reasonable default values
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");

    private double myMass;
    private Vector myAcceleration; 
    private Vector myGravity;
    
    //private static double DEFAULT_GRAVITY_ANGLE = 270.0; 
	//private static double DEFAULT_GRAVITY_MAGNITUDE = 0.000350169; 
    //private static double DEFAULT_VISCOSITY_SCALE = 0.0;
    
	//private double myViscosityScale; 


    public Mass (double x, double y, double mass) {
        super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
        myMass = mass;
        myAcceleration = new Vector();
        myGravity = new Vector();
    }
    
    /**
     * @return mass of this mass
     */
    public double getMass() { 
    	return myMass; 
    }
    
    /**
     * Returns the acceleration vector of this mass
     */
    public Vector getAcceleration(){
    	return myAcceleration;
    }
    
    /**
     * sets the gravity vector for this mass
     */
    public void setGravity(Vector gravity){
    	myGravity = gravity;
    }
    
    /**
     * Returns the gravity vector of this mass
     */
    public Vector getGravity(){
    	return myGravity;
    }
    
//    /**
//     * Sets gravity angle
//     */
//    public static void setGravityAngle(double angle) { 
//    	DEFAULT_GRAVITY_ANGLE = angle; 
//    }
//    
//    /**
//     * Sets gravity magnitude
//     */
//    public static void setGravityMagnitude(double magnitude) { 
//    	DEFAULT_GRAVITY_MAGNITUDE = magnitude; 
//    }
//    
//    /**
//	 * Calculates gravitational force 
//	 */
//	public Vector calculateGravitationalForce() { 
//		return new Vector(DEFAULT_GRAVITY_ANGLE, myMass * DEFAULT_GRAVITY_MAGNITUDE); 
//	}
	
//	/**
//	 * Return's Mass' viscosity scale
//	 */
//    public double getViscosityScale() {
//		return myViscosityScale;
//	}
//    
//    /**
//     * Set Mass' viscosity scale
//     */
//	public void setViscosityScale(double viscosity) {
//		myViscosityScale = viscosity;
//	}
	
//	private Vector calculateViscosityForce() { 
//		Vector viscosity = new Vector(myAcceleration); 
//		viscosity.negate(); 
//		viscosity.scale(DEFAULT_VISCOSITY_SCALE);
//		return viscosity; 
//	}
	
	
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        //checkBoundaries(bounds);
    	
    	//applyForce(myGravity); 
        //applyViscosity(); 
    	
    	applyForce(getBounce(bounds));
        // convert force back into Mover's velocity
        getVelocity().sum(myAcceleration);
        myAcceleration.reset();
        // move mass by velocity
        super.update(elapsedTime, bounds);
    }
    
    /**
     * checks if mass tries to move out of the screen and keeps it
     * within boundaries
     */
    private void checkBoundaries(Dimension bounds){
    	double centerY = getCenter().y;
    	double centerX = getCenter().x;
    	if(getTop() <= 0) setCenter(centerX,DEFAULT_SIZE.getHeight()/2);

    	if(getRight() >= bounds.getWidth()) 
    		setCenter(bounds.getWidth() - DEFAULT_SIZE.getWidth()/2,centerY);
    	
    	if(getBottom() >= bounds.getHeight()) 
    		setCenter(centerX,bounds.getHeight() - DEFAULT_SIZE.getHeight()/2);
    	
    	if(getLeft() <= 0) setCenter(DEFAULT_SIZE.getWidth()/2,centerY);
    }

//    /**
//     * XXX.
//     */
//    @Override
//    public void paint (Graphics2D pen) {
//        pen.setColor(Color.BLACK);
//        pen.fillOval((int)getLeft(), (int)getTop(), (int)getWidth(), (int)getHeight());
//    }

    /**
     * Use the given force to change this mass's acceleration.
     */
    public void applyForce (Vector force) {
    	myAcceleration.sum(force);  		 
    }

//    public void applyGravity () { 
//    	Vector noForce = new Vector(0,0);
//    	Vector gravity = calculateGravitationalForce(); 
//    	if (myMass < 0) { 
//    		myAcceleration.sum(noForce); 
//    	} else { 
//    		myAcceleration.sum(gravity); 
//    	}
//    }
    
//    public void applyViscosity() { 
//    	Vector noForce = new Vector(0,0);
//    	Vector viscosity = calculateViscosityForce(); 
//    	if (myMass < 0) { 
//    		myAcceleration.sum(noForce); 
//    	} else { 
//    		myAcceleration.sum(viscosity); 
//    	}
//    }
    
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
