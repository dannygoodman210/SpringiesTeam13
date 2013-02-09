package simulation;

import java.util.List;

import util.Vector;


/**
 * Class that represents viscosity and its functionalities
 * @author Henrique Moraes
 *
 */
public class ViscosityForce extends Environment{
	
	private double myViscosity;
	
	public ViscosityForce(double viscosity){
		myViscosity = viscosity;
	}
	
	/**
	 * Assigns the value to the viscosity of the environment
	 */
	public void setViscosity(double viscosity){
		myViscosity = viscosity;
	}
	
	/**
	 * retrieves the viscosity value from the environment
	 */
	public double getViscosity(){
		return myViscosity;
	}
	
	/**
	 * Receives the list of masses and applies the viscosity force
	 * on the current acceleration of the mass. The viscosity is 
	 * a scaled opposing vector to the acceleration of the mass
	 */
	public void applyForce(List<Mass> masses) { 
		for (Mass m : masses){
			Vector viscosity = new Vector(m.getAcceleration());
			viscosity.negate(); 
			viscosity.scale(myViscosity); 
			m.applyForce(viscosity);
		}
	}
}
