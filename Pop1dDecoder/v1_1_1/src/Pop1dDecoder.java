package acq_tam_wg.Pop1dDecoder.v1_1_1.src;

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

public class Pop1dDecoder extends NslJavaModule{

//NSL Version: 3_0_1
//Sif Version: 9
//libNickName: acq_tam_wg
//moduleName:  Pop1dDecoder
//versionName: 1_1_1


//variables 
public  NslDinDouble1 input; // 
public  NslDoutDouble0 output; // 
private double sum; // 

//methods 
public void simRun()
{
	processInput();
}

public void simTrain()
{
	processInput();
}

protected void processInput()
{
	output.set(0);
	sum=0;
	if(symmetric&&input.get(0)>=threshold&&input.get(size-1)>=threshold)
	{
		double negTotal=0, posTotal=0;
		for(int i=0; i<size; i++)
		{
			if(i<size/2)
				negTotal=negTotal+input.get(i);
			else
				posTotal=posTotal+input.get(i);
		}
		for(int i=0; i<size; i++)
		{
			if(input.get(i)>=threshold)
			{
				double prefVal=minVal+((double)i)*((maxVal-minVal)/(size-1));
				if(i<size/2&&posTotal>=negTotal)
					prefVal=prefVal+(maxVal-minVal);
				else if(i>size/2&&negTotal>posTotal)
					prefVal=prefVal+(minVal-maxVal);
				output.set(output.get()+input.get(i)*prefVal);
				sum=sum+input.get(i);
			}
		}
	}
	else
	{		
		for(int i=0; i<size; i++)
		{
			if(input.get(i)>=threshold)
			{
				double prefVal=minVal+((double)i)*((maxVal-minVal)/(size-1));
				output.set(output.get()+input.get(i)*prefVal);
				sum=sum+input.get(i);
			}
		}
	}
	output.set(output.get()/sum);
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
	int size;
	double minVal;
	double maxVal;
	double threshold;
	boolean symmetric;

	/* Temporary variables */

	/* GENERIC CONSTRUCTOR: */
	public Pop1dDecoder(String nslName, NslModule nslParent, int size, double minVal, double maxVal, double threshold, boolean symmetric)
{
		super(nslName, nslParent);
		this.size=size;
		this.minVal=minVal;
		this.maxVal=maxVal;
		this.threshold=threshold;
		this.symmetric=symmetric;
		initSys();
		makeInstPop1dDecoder(nslName, nslParent, size, minVal, maxVal, threshold, symmetric);
	}

	public void makeInstPop1dDecoder(String nslName, NslModule nslParent, int size, double minVal, double maxVal, double threshold, boolean symmetric)
{ 
		Object[] nslArgs=new Object[]{size, minVal, maxVal, threshold, symmetric};
		callFromConstructorTop(nslArgs);
		input = new NslDinDouble1("input", this, size);
		output = new NslDoutDouble0("output", this);
		callFromConstructorBottom();
	}

	/******************************************************/
	/*                                                    */
	/* End of automatic declaration statements.           */
	/*                                                    */
	/******************************************************/


}//end Pop1dDecoder

