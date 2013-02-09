package simulation;

import java.util.List;

import util.Vector;

/**
 * Class that represents the gravity vector and its functionalities
 * @author Henrique Moraes
 *
 */
public class GravityForce extends Environment{
	
	private Vector myGravity;
	
	public GravityForce(double angle, double magnitude){
		myGravity = new Vector(angle, magnitude);
	}
	
	/**
	 * Receives a gravity vector and assigns it to its private variable 
	 */
	public void setGravity(Vector gravity){
		myGravity = gravity;
	}
	
	/**
	 * Returns gravity vector to the caller
	 */
	public Vector getGravity(){
		return myGravity;
	}
	
	/**
	 * Applies the gravity force on each mass according to the mass'
	 * self gravity vector
	 */
	public void applyForce(List<Mass> masses){
		for(Mass m : masses){
			Vector massGravity = new Vector(myGravity);
			massGravity.scale(m.getMass());
			m.applyForce(massGravity);
		}
	}

}
