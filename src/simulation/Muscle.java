package simulation;

import java.awt.Dimension;

/**
 * 
 * @author Danny Goodman
 *
 */
public class Muscle extends Spring{

	protected double myAmplitude;
	protected double myInitLength;
	private static final double RADIANS_PER_FRAME = Math.PI/100;
	protected double myPhase;
	
	public Muscle(Mass start, Mass end, double initLength, double kVal, double amplitude) {
		super(start, end, initLength, kVal);
		myAmplitude = amplitude;
		myInitLength = initLength;
		myPhase = 0;
	}
	
	public void update(double elapsedTime, Dimension bounds){
		updateRestLength();
		super.update(elapsedTime, bounds);
	}

	private void updateRestLength() {
		myLength = myInitLength*(1+myAmplitude*Math.sin(myPhase));
		myPhase += RADIANS_PER_FRAME;
	}
	
	
	

}
