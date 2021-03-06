package simulation;

import java.awt.event.KeyEvent;
import java.util.List;
import util.Vector;


/**
 * Class that represents the gravity vector and its functionalities
 * 
 * @author Henrique Moraes
 * 
 */
public class GravityForce extends Environment {

    private Vector myGravity;

    /**
     * Constructor that represents the gravity force on the model
     * @param angle direction of the force
     * @param magnitude magnitude of the force
     */
    public GravityForce (double angle, double magnitude) {
        myGravity = new Vector(angle, magnitude);
        myName = "Gravity";
    }

    /**
     * Applies the gravity force on each mass according to the mass'
     * self gravity vector
     * Does not apply force if isForceOn is off
     */
    public void applyForce (List<Mass> masses) {
        if (!isForceOn) { return; }
        for (Mass m : masses) {
            Vector massGravity = new Vector(myGravity);
            massGravity.scale(m.getMass());
            m.applyForce(massGravity);
        }
    }

    @Override
    public final Environment toggleForce (final int key) {
        if (!(key == KeyEvent.VK_G)) { return null; }
        isForceOn = !isForceOn;
        return this;
    }
}
