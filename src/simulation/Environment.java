package simulation;

import java.awt.Dimension;
import java.util.List;

/**
 * Class that contains general information about the constructor
 * It stores gravity, viscosity, center of mass, and  informations
 * @author Henrique Moraes
 *
 */
public abstract class Environment {
	
	protected static Dimension myBounds;
	protected boolean isForceOn = true;
	protected String myName;
	
	/**
	 * Dynamically updates the bounds so all subclasses are aware of the 
	 * new state of the view
	 * @param bounds determined by the view
	 */
	public static void updateBounds(Dimension bounds){
		myBounds = bounds;
	}
	
	/**
	 * @return A string representing the status of this force. 
	 * Whether it is on or not
	 */
	public String isOnOff(){
		if(isForceOn) return "On";
		return "Off";
	}
	
	/**
	 * @return the name of this force
	 */
	public String getName(){
		return myName;
	}
	
	/**
	 * toggles the force of this object on and off
	 * @return this force
	 */
	public Environment toggleForce(){
		isForceOn = !isForceOn;
		return this;
	}
	
	/**
	 * Makes a force subclass apply its own force on each mass 
	 * @param List of masses to have force applied to them 
	 */
	public abstract void applyForce(List<Mass> masses);
	
}
