package simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import util.Vector;
import java.util.Scanner;


/**
 * Loads all the information from the data files
 * Passes all environment information to Environment class
 * and creates the necessary masses, springs, and muscles
 * 
 * @author Danny Goodman, Henrique Moraes, Thomas Varner
 */
public class Factory {
    // data file keywords
    private static final String MASS_KEYWORD = "mass";
    private static final String SPRING_KEYWORD = "spring";
    private static final String GRAVITY_KEYWORD = "gravity";
    private static final String VISCOSITY_KEYWORD = "viscosity";
    private static final String WALL_KEYWORD = "wall";
    private static final String CENTER_MASS_KEYWORD = "centermass";
    private static final String MUSCLE_KEYWORD = "muscle";

    // mass IDs
    Map<Integer, Mass> myMasses = new HashMap<Integer, Mass>();


    /**
     * XXX.
     */
    public void loadModel (Model model, File modelFile) {
        try {
            Scanner input = new Scanner(modelFile);
            while (input.hasNext()) {
                Scanner line = new Scanner(input.nextLine());
                if (line.hasNext()) {
                    String type = line.next();
                    if (MASS_KEYWORD.equals(type)) {
                        model.add(massCommand(line));
                    }
                    else if (SPRING_KEYWORD.equals(type)) {
                        model.add(springCommand(line));
                    }
                    else if (MUSCLE_KEYWORD.equals(type)) {
                        model.add(muscleCommand(line));
                    }
                    else if (GRAVITY_KEYWORD.equals(type)){
                    	model.getEnvironment().setGravity(gravityCommand(line));
                    }
                    else if (VISCOSITY_KEYWORD.equals(type)){
                    	model.getEnvironment().setViscosity(viscosityCommand(line));
                    	//viscosityCommand(line);
                    }
                    else if (CENTER_MASS_KEYWORD.equals(type)){
                    	model.getEnvironment().setCenterMass(centerMassCommand(line));
                    }
                    else if (WALL_KEYWORD.equals(type)){
                    	model.getEnvironment().setWallStats(wallCommand(line));
                    }
                }
            }
            input.close();
        }
        catch (FileNotFoundException e) {
            // should not happen because File came from user selection
            e.printStackTrace();
        }
    }
    
    /**
     * XXX.
     */
    public void loadEnvironment (Model model, File modelFile) {
        try {
            Scanner input = new Scanner(modelFile);
            while (input.hasNext()) {
                Scanner line = new Scanner(input.nextLine());
                if (line.hasNext()) {
                    String type = line.next();
                    if (GRAVITY_KEYWORD.equals(type)){
                    	model.getEnvironment().setGravity(gravityCommand(line));
                    }
                    else if (VISCOSITY_KEYWORD.equals(type)){
                    	model.getEnvironment().setViscosity(viscosityCommand(line));
                    }
                    else if (CENTER_MASS_KEYWORD.equals(type)){
                    	model.getEnvironment().setCenterMass(centerMassCommand(line));
                    }
                    else if (WALL_KEYWORD.equals(type)){
                    	model.getEnvironment().setWallStats(wallCommand(line));
                    }
                }
            }
            //added instantiates the gravity in masses
            model.initMassParameters();
            input.close();
        }
        catch (FileNotFoundException e) {
            // should not happen because File came from user selection
            e.printStackTrace();
        }
    }

    // create mass from formatted data
    private Mass massCommand (Scanner line) {
        int id = line.nextInt();
        double x = line.nextDouble();
        double y = line.nextDouble();
        double mass = line.nextDouble();
        Mass result;
        if (mass > 0)
        	result = new Mass(x, y, mass);
        else 
        	result = new FixedMass(x, y, mass);
        myMasses.put(id,  result);
        return result;
    }

    // create spring from formatted data
    private Spring springCommand (Scanner line) {
        Mass m1 = myMasses.get(line.nextInt());
        Mass m2 = myMasses.get(line.nextInt());
        double restLength = line.nextDouble();
        double ks = line.nextDouble();
        return new Spring(m1, m2, restLength, ks);
    }
    
    // create muscle from formatted data
    private Muscle muscleCommand (Scanner line) {
        Mass m1 = myMasses.get(line.nextInt());
        Mass m2 = myMasses.get(line.nextInt());
        double restLength = line.nextDouble();
        double ks = line.nextDouble();
        double amplitude = line.nextDouble();
        return new Muscle(m1, m2, restLength, ks, amplitude);
    }

    // add gravity information to Environment
    private Vector gravityCommand(Scanner line){
    	double angle = line.nextDouble();
    	double magnitude = line.nextDouble();
    	return new Vector(angle,magnitude);
    }

    // reads viscosity information to be added to environment
    private double viscosityCommand(Scanner line){
    	return line.nextDouble();
    }
    
    // reads center of mass parameters
    private double[] centerMassCommand(Scanner line){
    	double[] centerStats = new double[2];
    	centerStats[0] = line.nextDouble();
    	centerStats[1] = line.nextDouble();
    	return centerStats;
    }
    
    // Reads wall parameters
    private double[] wallCommand(Scanner line){
    	double[] wallStats = new double[3];
    	for(int i = 0; i<wallStats.length; i++){
    		wallStats[i] = line.nextDouble();
    	}
    	return wallStats;
    }
    
}
