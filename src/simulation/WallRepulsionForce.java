package simulation;

import java.awt.Dimension;
import java.util.List;

import util.Vector;

/**
 * Class that represents viscosity and its functionalities
 * @author Henrique Moraes
 *
 */
public class WallRepulsionForce extends Environment{
	
	//Directions for vetors
	public static final double DOWN_DIRECTION = 90;
	public static final double LEFT_DIRECTION = 180;
	public static final double UP_DIRECTION = 270;
	public static final double RIGHT_DIRECTION = 0;
	
	private double myForceDirection;
	private double myID;
	private double myMagnitude;
	private double myExponentialIndex;
	
	public WallRepulsionForce(double ID, double direction,
			double magnitude, double exponent){
		myID = ID;
		myForceDirection = direction;
		myMagnitude = magnitude;
		myExponentialIndex = exponent;
	}
	
	/**
	 * Calculates and applies wall repulsion force to each mass
	 */
	public void applyForce(List<Mass> masses){
		for(Mass m : masses){
			m.applyForce(calculateWallRepulsion(m));
		}
	}

	/**
	 * Calculates the force done by a wall depending on the wall ID
	 */
	private Vector calculateWallRepulsion(Mass m){
		double distance;
		//upper wall
		if(myID == 0){
			distance = m.getY();
		}
		//Right wall
		else if (myID == 1){
			distance = myBounds.getWidth() - m.getX();
		}
		//Bottom wall
		else if (myID == 2){
			distance = myBounds.getHeight() - m.getY();
		}
		//Left wall
		else{
			distance = m.getX();
		}
		return new Vector(myForceDirection, 
				myMagnitude/(Math.pow(distance, myExponentialIndex)));
	}
}
