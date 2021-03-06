package simulation;

import java.awt.event.KeyEvent;
import java.util.List;
import util.Location;
import util.Vector;


/**
 * Class that represents the center of mass force and its functionalities
 * 
 * @author Henrique Moraes
 * 
 */
public class CenterMassForce extends Environment {

    private double myMagnitude;
    private double myExponentialIndex;
    private Mass myCenterMass;

    /**
     * Constructor for creating a force generated by the center of mass 
     * of the masses on the screen
     * @param magnitude magnitude of the center of mass
     * @param exponent exponent used on the distance of the mass
     * from the system's center of mass. The force is inversely
     * proportional to the distance.
     */
    public CenterMassForce (double magnitude, double exponent) {
        myMagnitude = magnitude;
        myExponentialIndex = exponent;
        myName = "Center Mass";
    }

    /**
     * Holds the information for center of mass calculation
     */
    public Mass getCenterMass () {
        return myCenterMass;
    }

    /**
     * Calculates the vector that represents the force exerted by
     * the center of mass
     * Applies the force exerted by the center of mass to each mass
     * Does not apply force if isForceOn is off
     * @param masses List with the masses of the model
     */
    public void applyForce (List<Mass> masses) {
        if (!isForceOn) { return; }
        myCenterMass = calculateCenterOfMass(masses);
        Location centerLocation = myCenterMass.getCenter();
        for (Mass m : masses) {
            double distance = myCenterMass.distance(m);
            double force = myMagnitude /
                           (Math.pow(distance, myExponentialIndex));
            Vector massesDirection = new Vector(m.getCenter(), centerLocation);
            Vector vectorForce = new Vector(massesDirection.getDirection(),
                                            force);
            m.applyForce(vectorForce);
        }
    }

    /**
     * Calculates the center of mass and retrieves a mass with zero mass
     * and a location. A mass object is returned because of the
     * convenience of the distance() method
     */
    private Mass calculateCenterOfMass (List<Mass> masses) {
        // Total mass will always be the same, not sure if necessary
        // to do it every time or set it from Model only once
        double totalMass = 0;
        double massPositionSumX = 0;
        double massPositionSumY = 0;
        for (Mass m : masses) {
            totalMass += m.getMass();
            massPositionSumX += m.getMass() * m.getX();
            massPositionSumY += m.getMass() * m.getY();
        }
        return new Mass(massPositionSumX / totalMass,
                        massPositionSumY / totalMass, 0);
    }

    @Override
    public final Environment toggleForce (final int key) {
        if (!(key == KeyEvent.VK_M)) { return null; }
        isForceOn = !isForceOn;
        return this;
    }
}
