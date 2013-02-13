package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import util.Text;
import view.Canvas;

/**
 * Class created to handle the inputs of the game
 * @author Henrique Moraes, Thomas Varner 
 *
 */
public class Control {
	private static final int RATE_OF_RESIZE = 2;
	private static final int MINIMUM_VIEW_HEIGHT = 100;
	
	private int keyCounter = 0;
	private Canvas myView;
	private Factory myFactory; 
	
	private int pixelsToResize = 0;
	private int colorCounter = 0;
	private String myToggledForceName = "";
	private String clearAssemblyLabel = ""; 
	private String myLastToggleStatus;
	private Text myStatus = new Text("");
	
	private GravityForce myGravityForce;
    private CenterMassForce myCenterMassForce;
    private ViscosityForce myViscosityForce;
    private WallRepulsionForce[] myWallRepulsionForces;
	
    public Control(Canvas canvas, Factory factory){
    	myView = canvas;
    	myFactory = factory; 
    	myWallRepulsionForces = new WallRepulsionForce[4];
    }
    
    /**
     * Adds the force to its designated place in control
     * @param force force to be added to control
     */
    public void add(Environment force){
    	if(force instanceof GravityForce) 
        	myGravityForce = (GravityForce) force;
        
        if(force instanceof ViscosityForce) 
        	myViscosityForce = (ViscosityForce) force;
        
        if(force instanceof CenterMassForce) 
        	myCenterMassForce = (CenterMassForce) force;
        
        if(force instanceof WallRepulsionForce) {
        	WallRepulsionForce wallForce = (WallRepulsionForce) force;
        	myWallRepulsionForces[wallForce.getID()-1] = wallForce;
        }
    }
    
    /**
     * Paints the status of inputs to notify the user
     * @param pen Graphics of the view
     */
    public void paint(Graphics2D pen){
    	if(colorCounter > 255){
        	colorCounter = 0;
        	myToggledForceName = "";
        	clearAssemblyLabel = ""; 
        }
        if(myToggledForceName.length() != 0){
        	myStatus.setText(myToggledForceName + " " + myLastToggleStatus);
        	myStatus.paint(pen, new Point(myView.getWidth()/2,myView.getHeight()/2), 
        			new Color(colorCounter,colorCounter,colorCounter));
        	colorCounter += 8;
        }
        if(clearAssemblyLabel.length() != 0){
        	myStatus.setText(clearAssemblyLabel);
        	myStatus.paint(pen, new Point(myView.getWidth()/2,myView.getHeight()/2), 
        			new Color(colorCounter,colorCounter,colorCounter));
        	colorCounter += 8;
        } 
    }
    
    /**
     * Updates the simulation's state based on inputs
     */
    public void update(){
    	keyCounter++;
    	checkInputs();
    	checkResize(myView.getSize()); 
    }
    
    public void clear() { 
    	clearAssemblyLabel = "Assemblies Cleared"; 
    	myFactory.clear(); 
    }
    
	/**
	 * Checks whether a key was pressed and apply its effects
	 */
	private void checkInputs(){
		
	    	if(keyCounter < 6) return;
	    	
	    	int key = myView.getLastKeyPressed();
	    	 
	    	Environment toggledForce = null;

	    	if (key == KeyEvent.VK_G) {
	    		toggledForce = myGravityForce.toggleForce();
	    	}
	    	else if (key == KeyEvent.VK_V) {
	    		toggledForce = myViscosityForce.toggleForce();
	    	}
	    	else if (key == KeyEvent.VK_M) {
	    		toggledForce = myCenterMassForce.toggleForce();
	    	}
	    	else if (key == KeyEvent.VK_1){
	    		toggledForce = myWallRepulsionForces[0].toggleForce();
	    	}
	    	else if (key == KeyEvent.VK_2){
	    		toggledForce = myWallRepulsionForces[1].toggleForce();
	    	}
	    	else if (key == KeyEvent.VK_3){
	    		toggledForce = myWallRepulsionForces[2].toggleForce();
	    	}
	    	else if (key == KeyEvent.VK_4){
	    		toggledForce = myWallRepulsionForces[3].toggleForce();	
	    	} 
	    	else if (key == KeyEvent.VK_UP){
	    		//Need constant??
	    		pixelsToResize = 10;
	    	}
	    	else if (key == KeyEvent.VK_DOWN){
	    		//Need constant??
	    		pixelsToResize = -10;
	    	}
	    	else if (key == KeyEvent.VK_N) { 
	    		//resets last key back to -1 so that the game can continue 
	    		myView.resetLastKey();   
	    		myFactory.loadNewModel();
	    	}
	    	else if (key == KeyEvent.VK_C) { 
	    		clear(); 
	    	}
	    	if(toggledForce != null){
	    		myToggledForceName = toggledForce.getName();
	    		myLastToggleStatus = toggledForce.isOnOff();
	    		keyCounter = 0;
	    		colorCounter = 0;
	    	}  
	}
	
	/**
	 * Checks if the view has to be resized
	 */
	private void checkResize(Dimension bounds){
    	if(bounds.height < MINIMUM_VIEW_HEIGHT && pixelsToResize < 0){
    		pixelsToResize = 0;
    		return;
    	}
		if(pixelsToResize > 0){
    		myView.setSize(bounds.width+1, bounds.height+1);
    		pixelsToResize -= RATE_OF_RESIZE;
    	}
    	else if(pixelsToResize < 0){
    		myView.setSize(bounds.width-1, bounds.height-1);
    		pixelsToResize += RATE_OF_RESIZE;
    	}
    }
}
