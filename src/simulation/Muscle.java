package simulation;

import java.awt.Dimension;

import util.Vector;

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
		// TODO Auto-generated constructor stub
	}
	
	public void update(double elapsedTime, Dimension bounds){
		updateRestLength();
		double dx = myStart.getX() - myEnd.getX();
        double dy = myStart.getY() - myEnd.getY();
        // apply hooke's law to each attached mass
        Vector force = new Vector(Vector.angleBetween(dx, dy), 
                                  myK * (myLength - Vector.distanceBetween(dx, dy)));
        myStart.applyForce(force);
        force.negate();
        myEnd.applyForce(force);
        // update sprite values based on attached masses
        setCenter(getCenter(myStart, myEnd));
        setSize(getSize(myStart, myEnd));
        setVelocity(Vector.angleBetween(dx, dy), 0);
	}

	private void updateRestLength() {
		myLength = myInitLength*(1+myAmplitude*Math.sin(myPhase));
		myPhase += RADIANS_PER_FRAME;
	}
	
	
	

}
