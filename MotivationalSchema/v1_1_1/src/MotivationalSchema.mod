package acq_tam_wg.MotivationalSchema.v1_1_1.src;
nslImport acq_tam_wg.Hypothalamus.v1_1_1.src.*;
nslImport acq_tam_wg.TAMCritic.v1_1_1.src.*;
nslImport acq_tam_wg.WGCritic.v1_1_1.src.*;

nslJavaModule MotivationalSchema(int numDrives, int angleRepSize, int mapSize, double[] d_min, double[] d_max){

//NSL Version: 3_0_n
//Sif Version: 9
//libNickName: acq_tam_wg
//moduleName:  MotivationalSchema
//versionName: 1_1_1
//floatSubModules: true


//variables 
public acq_tam_wg.Hypothalamus.v1_1_1.src.Hypothalamus hypothalamus(numDrives, d_min, d_max); // 
public acq_tam_wg.TAMCritic.v1_1_1.src.TAMCritic tamCritic(angleRepSize, numDrives, d_min, d_max); // 
public NslDinDouble1 reductions(numDrives); // 
public NslDinDouble1 incentives(numDrives); // 
public NslDoutDouble1 motivationalState(numDrives); // 
public acq_tam_wg.WGCritic.v1_1_1.src.WGCritic wgCritic(numDrives, d_min, d_max); // 
public NslDinInt0 currentNodeId(); // 
public NslDinDouble0 lastNodeDist(); // 
public NslDinDouble1 currentNodeDesirability(numDrives); // 
public NslDoutDouble1 nodeReinforcement(numDrives); // 
public NslDinDouble1 lastNodeDesirability(numDrives); // 
public NslDinDouble2 currentLocalDesirability(numDrives, angleRepSize); // 
public NslDoutDouble1 tamReinforcement(numDrives); // 

//methods 

public void makeConn(){
    nslRelabel(reductions,hypothalamus.reductions);
    nslRelabel(reductions,tamCritic.rewards);
    nslRelabel(reductions,wgCritic.rewards);
    nslRelabel(lastNodeDist,wgCritic.lastNodeDist);
    nslRelabel(incentives,hypothalamus.incentives);
    nslRelabel(currentNodeDesirability,wgCritic.currentNodeDesirability);
    nslRelabel(currentNodeId,wgCritic.currentNodeId);
    nslRelabel(currentNodeId,tamCritic.currentNodeId);
    nslRelabel(lastNodeDesirability,wgCritic.lastNodeDesirability);
    nslRelabel(currentLocalDesirability,tamCritic.currentDesirability);
    nslConnect(hypothalamus.drives,tamCritic.motivations);
    nslRelabel(hypothalamus.drives,motivationalState);
    nslConnect(hypothalamus.drives,wgCritic.motivations);
    nslRelabel(tamCritic.reinforcement,tamReinforcement);
    nslRelabel(wgCritic.reinforcement,nodeReinforcement);
}
}//end MotivationalSchema

