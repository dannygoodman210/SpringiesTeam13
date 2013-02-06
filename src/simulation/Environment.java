package simulation;

import java.awt.Dimension;
import java.util.List;
import util.Vector;
import util.Location;

/**
 * Class that contains general information about the constructor
 * It stores gravity, viscosity, center of mass, and  informations
 * @author Henrique Moraes
 *
 */
public class Environment {
	private Vector myGravity;
	private double myViscosity;
	private double[] myCenterStats = {3, 0};
	private double[][] wallStats = new double[4][2];
	
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
	 * Holds the information for center of mass calculation
	 */
	public void setCenterMass(double[] center){
		myCenterStats = center;
	}
	
	/**
	 * Assigns the value to the viscosity of the environment
	 */
	public void setViscosity(double viscosity){
		myViscosity = viscosity;
	}
	
	/**
	 * Assigns wall information to environment
	 * wallStats is a matrix that holds each 
	 */
	public void setWallStats(double[] stats){
		wallStats[(int) (stats[0]-1)][0]=stats[1];
		wallStats[(int) (stats[0]-1)][1]=stats[2];
	}
	
	/**
	 * To avoid repetitive calls in the Model, only this 
	 * method has to be called and it will apply wall repulsion,
	 * center of mass, gravity, and viscosity forces on each mass
	 */
	public Mass applyForces(List<Mass> masses, Dimension bounds){
		Mass centerMass = calculateCenterOfMass(masses);
		for(Mass m : masses){	
			applyGravity(m);
			applyCenterOfMassForce(m, centerMass);
			applyWallRepulsion(m, bounds);
			applyViscosityForce(m);
		}
		return centerMass;
	}
	
	/**
	 * Applies the gravity force on each mass according to the mass'
	 * self gravity vector
	 */
	private void applyGravity(Mass m){
		m.applyForce(m.getGravity());
	}
	
	/**
	 * Calculates and applies wall repulsion force to each mass
	 */
	private void applyWallRepulsion(Mass m, Dimension bounds){
		for(int i = 0; i<wallStats.length; i++){
			m.applyForce(calculateWallRepulsion(m, i, bounds));
		}
	}

	/**
	 * Calculates the force done by a wall depending on the wall ID
	 */
	private Vector calculateWallRepulsion(Mass m, int ID, Dimension bounds){
		double magnitude = wallStats[ID][0];
		double exponent = wallStats[ID][1];
		//upper wall
		if(ID == 0){
			double distance = m.getY();
			return new Vector(90, magnitude/(Math.pow(distance, exponent)));
		}
		//Right wall
		else if (ID == 1){
			double distance = bounds.getWidth() - m.getX();
			return new Vector(180, magnitude/(Math.pow(distance, exponent)));
		}
		//Bottom wall
		else if (ID == 2){
			double distance = bounds.getHeight() - m.getY();
			return new Vector(270, magnitude/(Math.pow(distance, exponent)));
		}
		//Left wall
		else{
			double distance = m.getX();
			return new Vector(0, magnitude/(Math.pow(distance, exponent)));
		}
	}
	
	/**
	 * Receives the list of masses and applies the viscosity force
	 * on the current acceleration of the mass. The viscosity is 
	 * a scaled opposing vector to the acceleration of the mass
	 */
	private void applyViscosityForce(Mass m) { 
		Vector viscosity = new Vector(m.getAcceleration());
		viscosity.negate(); 
		viscosity.scale(myViscosity); 
		m.applyForce(viscosity);
	}

	/**
	 * Calculates the vector that represents the force exerted by 
	 * the center of mass
	 * Applies the force exerted by the center of mass to each mass
	 */
	private Mass applyCenterOfMassForce(Mass m, Mass centerMass){
		Location centerLocation = centerMass.getCenter();
			double distance = centerMass.distance(m);
			double Force = myCenterStats[0]/
					(Math.pow(distance, myCenterStats[1]));
			Vector massesDirection = new Vector(m.getCenter(),centerLocation);
			Vector vectorForce = new Vector(massesDirection.getDirection(),
				Force);
			m.applyForce(vectorForce);
		return centerMass;
	}
	
	/**
	 * retrieves the viscosity value from the environment
	 */
	public double getViscosity(){
		return myViscosity;
	}
	
	/**
	 * Calculates the center of mass and retrieves a mass with zero mass
	 * and a location. A mass object is returned because of the 
	 * convenience of the distance() method
	 */
	private Mass calculateCenterOfMass(List<Mass> masses){
		//Total mass will always be the same, not sure if necessary
		//to do it every time or set it from Model only once
		double totalMass = 0;
		double massPositionSumX = 0;
		double massPositionSumY = 0;
		for(Mass m : masses){
			totalMass += m.getMass();
			massPositionSumX += m.getMass()*m.getX();
			massPositionSumY += m.getMass()*m.getY();
		}
		return new Mass(massPositionSumX/totalMass,
				massPositionSumY/totalMass,0);
	}
	
}
