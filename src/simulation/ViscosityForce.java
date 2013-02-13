package simulation;

import java.awt.event.KeyEvent;
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
        myName = "Viscosity";
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
     * Does not apply force if isForceOn is off
     */
    public void applyForce(List<Mass> masses) { 
        if (!isForceOn) { return; }
        for (Mass m : masses) {
            Vector viscosity = new Vector(m.getVelocity());
            viscosity.negate();
            viscosity.scale(myViscosity);
            m.applyForce(viscosity);
        }
    }

    @Override
    public final Environment toggleForce(final int key) {
        if (!(key == KeyEvent.VK_V)) { return null; }
        isForceOn = !isForceOn;
        return this;
    }
}
