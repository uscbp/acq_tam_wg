package acq_tam_wg.DNF.v1_1_1.src;

nslJavaModule DNF(int dim){

//NSL Version: 3_0_1
//Sif Version: 9
//libNickName: acq_tam_wg
//moduleName:  DNF
//versionName: 1_1_1


//variables 
public NslDinDouble1 input(dim); // 
public NslDoutDouble1 output(dim); // 
private NslDouble1 weightKernel(2*dim); // 
private NslDouble0 w_excite; // 
private NslDouble0 sigma_w; // 
private NslDouble0 w_inhibit; // 
private NslDouble0 h; // 
private NslDouble1 u(dim); // 
private NslDouble0 tau; // 
private NslDouble0 beta; // 
private NslDouble0 u_0; // 
private NslDouble0 q; // 
private NslDouble1 noise(dim); // 
private boolean initialized=false; // 

//methods 
public void initModule()
{
	h=-1;
	tau=.075;
	beta=1.5;
	u_0=0.0;
	w_excite=13.0;
	w_inhibit=20.0;
	sigma_w=20.0;
	q=5.0;
	h.nslSetAccess('W');
	w_excite.nslSetAccess('W');
	w_inhibit.nslSetAccess('W');
	sigma_w.nslSetAccess('W');
	q.nslSetAccess('W');
	updateWeightKernel();
	if(!initialized)
	{
		//nslAddSpatialCanvas("output", "weight", weightKernel, -1, 1);
		//nslAddSpatialCanvas("output", "output", output, 0, 1);
		nslSetColumns(1,"output");
		addPanel("dNFParameters", "input");
		addLabelToPanel("noiseStrength", "dNFParameters", "input");
		NslSlider ns_q=addSliderToPanel("noiseStrength", "dNFParameters", "input", NslSlider.HORIZONTAL);
		ns_q.setMinimum(0);
		ns_q.setMaximum(10);
		ns_q.setValue((int)q.get());
		addLabelToPanel("h", "dNFParameters", "input");
		NslSlider ns_h=addSliderToPanel("h", "dNFParameters", "input", NslSlider.HORIZONTAL);
		ns_h.setMinimum(-5);
		ns_h.setMaximum(0);
		ns_h.setValue((int)h.get());
		addLabelToPanel("sigma_w", "dNFParameters", "input");
		NslSlider ns_sigma_w=addSliderToPanel("sigma_w", "dNFParameters", "input", NslSlider.HORIZONTAL);
		ns_sigma_w.setMinimum(0);
		ns_sigma_w.setMaximum(20);
		ns_sigma_w.setValue((int)sigma_w.get());
		addLabelToPanel("w_excite", "dNFParameters", "input");
		NslSlider ns_w_excite=addSliderToPanel("w_excite", "dNFParameters", "input", NslSlider.HORIZONTAL);
		ns_w_excite.setMinimum(0);
		ns_w_excite.setMaximum(2000);
		ns_w_excite.setValue((int)(w_excite.get()*100));
		addLabelToPanel("w_inhibit", "dNFParameters", "input");
		NslSlider ns_w_inhibit=addSliderToPanel("w_inhibit", "dNFParameters", "input", NslSlider.HORIZONTAL);
		ns_w_inhibit.setMinimum(0);
		ns_w_inhibit.setMaximum(2000);
		ns_w_inhibit.setValue((int)(w_inhibit.get()*100));
		initialized=true;
	}
}

public void initRun()
{
	init();
}

public void initTrain()
{
	init();
}

public void init()
{
	output=0;
	u=0;
}

public void hValueChanged(int value)
{
	if(initialized)
	{
		h=(double)value;
	}
}

public void sigma_wValueChanged(int value)
{
	if(initialized)
	{
		sigma_w=(double)value;
		updateWeightKernel();
	}
}

public void w_exciteValueChanged(int value)
{
	if(initialized)
	{
		w_excite=(double)(value/100.0);
		updateWeightKernel();
	}
}

public void w_inhibitValueChanged(int value)
{
	if(initialized)
	{
		w_inhibit=(double)(value/100.0);
		updateWeightKernel();
	}
}

public void noiseStrengthValueChanged(int value)
{
	if(initialized)
	{
		q=value;
	}
}

public void updateWeightKernel()
{
	for(int i=0; i<dim*2; i++)
	{
		weightKernel[i]=w_excite*nslExp((double)(-(i-dim)*(i-dim))/(2.0*sigma_w*sigma_w))-w_inhibit;
	}
}

public void simRun()
{
	process();
}

public void simTrain()
{
	process();
}

protected void process()
{
	u=nslDiff(u, tau.get(), -u + h + input + nslConv(weightKernel, output) + q*nslRandom(noise));
	output=1.0/(1.0+nslExp(-beta*(u-u_0)));
}
public void makeConn(){
}
}//end DNF

