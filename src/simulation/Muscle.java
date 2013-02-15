package simulation;

import java.awt.Dimension;


/**
 * Muscle class extends Spring class. Functions as a Spring with changing rest length.
 * 
 * @author Danny Goodman
 */
public class Muscle extends Spring {

    private static final int FRAME_RATE_DENOM = 100;
    private static final double RADIANS_PER_FRAME = Math.PI / FRAME_RATE_DENOM;
    private double myAmplitude;
    private double myInitLength;
    private double myPhase;

    /**
     * Constructor for Muscle. Different from Spring Constructor in that it adds amplitude.
     * 
     * @param start : mass 1 connected by Muscle
     * @param end : mass 2 connected by Muscle
     * @param initLength : starting rest length of Muscle
     * @param kVal : Stiffness
     * @param amplitude : describes changing rest length function
     */
    public Muscle (Mass start, Mass end, double initLength, double kVal, double amplitude) {
        super(start, end, initLength, kVal);
        myAmplitude = amplitude;
        myInitLength = initLength;
        myPhase = 0;
    }

    /**
     * Update the Muscle's RestLength and call super class update
     * to update forces and position.
     * 
     * @param elapsedTime : Time elapsed since last update
     * @param bounds : Bounds of JFrame window for bouncing purposes
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        updateRestLength();
        super.update(elapsedTime, bounds);
    }

    private void updateRestLength () {
        setLength(myInitLength * (1 + myAmplitude * Math.sin(myPhase)));
        myPhase += RADIANS_PER_FRAME;
    }

}
