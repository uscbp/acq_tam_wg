package acq_tam_wg.TAMCritic.v1_1_2.src;

/*********************************/
/*                               */
/*   Importing all Nsl classes   */
/*                               */
/*********************************/

import nslj.src.system.*;
import nslj.src.cmd.*;
import nslj.src.lang.*;
import nslj.src.math.*;
import nslj.src.display.*;
import nslj.src.display.j3d.*;

/*********************************/

public class TAMCritic extends NslJavaModule{

//NSL Version: 3_0_1
//Sif Version: 9
//libNickName: acq_tam_wg
//moduleName:  TAMCritic
//versionName: 1_1_2


//variables 
private  NslDouble2 nodeDesirability; // 
private int currentNodeId; // 
private int lastNodeId; // 
private double alpha; // 
private double appetitiveDriveGamma; // 
public  NslDinDouble1 rewards; // 
public  NslDinDouble1 motivations; // 
public  NslDinInt0 currentNodeIdIn; // 
public  NslDoutDouble1 reinforcement; // 
private double aversiveDriveGamma; // 

//methods 
public void initModule()
{
	// learning rate
	alpha=0.1;
	// discount rate
	appetitiveDriveGamma=0.5;
	aversiveDriveGamma=0.01;
}

public void reset()
{
	// Reset node Ids
	lastNodeId=-1;
	currentNodeId=-1;
}

/**
 * Executed before each training epoch
 */
public void initTrain()
{
	reset();
}

/**
 * Executed at the start of each run epoch
 */
public void initRun()
{
	reset();
}

/**
 * Executed during each training cycle
 */
public void simTrain()
{
	// Update node IDs
	updateNodeIds();

	// Update value
	updateStateValues();

	// Update reinforcement
	updateReinforcement();
}

/**
 * Executed during each run cycle
 */
public void simRun()
{
	// Update node IDs
	updateNodeIds();

	// Update reinforcement
	updateReinforcement();
}

/**
 * Update current and last node ID
 */
protected void updateNodeIds()
{
	if(currentNodeIdIn.get()>-1)
	{
		if(currentNodeIdIn.get()!=currentNodeId&&currentNodeId>-1)
			lastNodeId=currentNodeId;
		currentNodeId=currentNodeIdIn.get();
	}
}

/**
 * Update value of last node
 */
protected void updateStateValues()
{
	// For each drive
	for(int i=0; i<numDrives; i++)
	{
		// Initialize TD error
		double tdError=0;

		// If we know what node we're in
		if(currentNodeIdIn.get()>-1)
		{
			// If we've moved to a new node, update the last node's desirability
			if(lastNodeId!=currentNodeIdIn.get()&&lastNodeId>-1)
			{
				// compute TD error
				if(i<2)
					tdError=(motivations.get(i)-d_min[i])/(d_max[i]-d_min[i])*(appetitiveDriveGamma*nodeDesirability.get(currentNodeIdIn.get(),i)-nodeDesirability.get(lastNodeId,i));
				else
					tdError=(motivations.get(i)-d_min[i])/(d_max[i]-d_min[i])*(aversiveDriveGamma*nodeDesirability.get(currentNodeIdIn.get(),i)-nodeDesirability.get(lastNodeId,i));

				// update last node desirability
				nodeDesirability.set(lastNodeId, i,NslMin.eval(d_max[i], nodeDesirability.get(lastNodeId,i)+alpha*tdError));
			}

			// Compute TD error for current node
			tdError=rewards.get(i)*(motivations.get(i)-d_min[i])/(d_max[i]-d_min[i]);

			// Update current node with rewards
			nodeDesirability.set(currentNodeIdIn.get(), i, NslMin.eval(d_max[i], nodeDesirability.get(currentNodeIdIn.get(),i)+alpha*tdError));
		}
	}
}

/**
 * Update reinforcement output
 */
protected void updateReinforcement()
{
	if(currentNodeIdIn.get()>-1)
		reinforcement.set(nodeDesirability.get(currentNodeIdIn.get()));
}

/**
 * Save state values to file
 */
public void saveWeights(String filename)
{
	NslTextFile file = new NslTextFile("file", this, filename);
        
	file.open('W');
	for(int i=0; i<maxNodes; i++)
	{
		for(int j=0; j<numDrives; j++)
		{
			file.puts(nodeDesirability.get(i, j));
		}
	}
	file.flush();
	file.close();
}

/**
 * Load state values from file
 */
public void loadWeights(String filename)
{
	NslTextFile file = new NslTextFile("file", this, filename);
        
	file.open('R');
	for(int i=0; i<maxNodes; i++)
	{
		for(int j=0; j<numDrives; j++)
		{
			nodeDesirability.set(i, j, Double.parseDouble(file.gets()));
		}
	}
	file.close();
}
public void makeConn(){
}

	/******************************************************/
	/*                                                    */
	/* Generated by nslc.src.NslCompiler. Do not edit these lines! */
	/*                                                    */
	/******************************************************/

	/* Constructor and related methods */
	/* makeinst() declared variables */

	/* Formal parameters */
	int maxNodes;
	int numDrives;
	double[] d_min;
	double[] d_max;
	double maxDesirability;

	/* Temporary variables */

	/* GENERIC CONSTRUCTOR: */
	public TAMCritic(String nslName, NslModule nslParent, int maxNodes, int numDrives, double[] d_min, double[] d_max, double maxDesirability)
{
		super(nslName, nslParent);
		this.maxNodes=maxNodes;
		this.numDrives=numDrives;
		this.d_min=d_min;
		this.d_max=d_max;
		this.maxDesirability=maxDesirability;
		initSys();
		makeInstTAMCritic(nslName, nslParent, maxNodes, numDrives, d_min, d_max, maxDesirability);
	}

	public void makeInstTAMCritic(String nslName, NslModule nslParent, int maxNodes, int numDrives, double[] d_min, double[] d_max, double maxDesirability)
{ 
		Object[] nslArgs=new Object[]{maxNodes, numDrives, d_min, d_max, maxDesirability};
		callFromConstructorTop(nslArgs);
		nodeDesirability = new NslDouble2("nodeDesirability", this, maxNodes, numDrives);
		rewards = new NslDinDouble1("rewards", this, numDrives);
		motivations = new NslDinDouble1("motivations", this, numDrives);
		currentNodeIdIn = new NslDinInt0("currentNodeIdIn", this);
		reinforcement = new NslDoutDouble1("reinforcement", this, numDrives);
		callFromConstructorBottom();
	}

	/******************************************************/
	/*                                                    */
	/* End of automatic declaration statements.           */
	/*                                                    */
	/******************************************************/


}//end TAMCritic

