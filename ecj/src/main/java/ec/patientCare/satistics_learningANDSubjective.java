package ec.patientCare;
import ec.*;
import ec.util.*;
import java.io.*;
import ec.vector.*;
import ec.util.Parameter;

public class satistics_learningANDSubjective extends Statistics {
	 // The parameter string and log number of the file for our readable population
    public static final String P_POPFILE = "pop-file";
    public int popLog;
    
    // The parameter string and log number of the file for our best-genome-#0 individual
    public static final String P_INFOFILE = "info-file";
    public int infoLog;
    
    //This is for model details documentation
    public static final String P_CAREFILE = "care-file";
    public int careLog;
    
    public void setup(final EvolutionState state, final Parameter base)
    {
    // DO NOT FORGET to call super.setup(...) !!
    super.setup(state,base);
    
    // set up popFile
    File popFile = state.parameters.getFile(
        base.push(P_POPFILE),null);
    if (popFile!=null) try
        {
        popLog = state.output.addLog(popFile,true);
        }
    catch (IOException i)
        {
        state.output.fatal("An IOException occurred while trying to create the log " + 
            popFile + ":\n" + i);
        }

    // similarly we set up infoFile
    File infoFile = state.parameters.getFile(
        base.push(P_INFOFILE),null);
    if (infoFile!=null) try
        {
        infoLog = state.output.addLog(infoFile,true);
        }
    catch (IOException i)
        {
        state.output.fatal("An IOException occurred while trying to create the log " + 
            infoFile + ":\n" + i);
        }
    
    //finally care file
    File careFile = state.parameters.getFile(
    		base.push(P_CAREFILE), null);
    if (careFile!=null) try
    	{
    	careLog = state.output.addLog(careFile, true);
    	}
    catch (IOException i)
    	{
    	state.output.fatal("An IOException occurred while trying to create the log " + 
                infoFile + ":\n" + i);
    	}
    		

    }
    
    public void postEvaluationStatistics(final EvolutionState state)
    {
    // be certain to call the hook on super!
    super.postEvaluationStatistics(state);
    
    // write out a warning that the next generation is coming 
    state.output.println("-----------------------\nGENERATION " + 
            state.generation + "\n-----------------------", popLog);
    
    // print out the population 
    state.population.printPopulation(state,popLog);
    
    // Encuentra el mejor individuo de esta generación - evaluación
    int best = 0;
    //double best_val = ((IntegerVectorIndividual)state.population.subpops.get(0).individuals.get(0)).genome[0];
    double best_val = ((DoubleVectorIndividual)state.population.subpops.get(0).individuals.get(0)).fitness.fitness();
    	for(int y=1;y<state.population.subpops.get(0).individuals.size();y++)
            {
            // We'll be unsafe and assume the individual is a DoubleVectorIndividual
            //double val = ((IntegerVectorIndividual)state.population.subpops.get(0).individuals.get(y)).genome[0];
            double val = ((DoubleVectorIndividual)state.population.subpops.get(0).individuals.get(y)).fitness.fitness();

            if (val > best_val)
                {
                best = y;
                best_val = val;
                }
            }
    state.population.subpops.get(0).individuals.get(best).printIndividualForHumans(state,infoLog);
    
    }

    public void preInitializationStatistics(final EvolutionState state) {
    	
        super.preInitializationStatistics(state);

    	
    	int int_param;
    	double doub_param;
    	String str_param;

    	Parameter par_gen = new Parameter("generations");
    	Parameter par_popsize = new Parameter("pop.subpop.0.size");
    	Parameter par_mutProb = new Parameter("pop.subpop.0.species.mutation-prob");
    	Parameter par_mutType = new Parameter("pop.subpop.0.species.mutation-type");
    	Parameter par_mutStDev = new Parameter("pop.subpop.0.species.mutation-stdev");

    	state.output.print("inicio run info\n", infoLog);
    	
    	int_param = state.parameters.getInt(par_gen, null);
    	state.output.print("generations: "+Integer.toString(int_param)+"\n", infoLog);
    	int_param = state.parameters.getInt(par_popsize, null);
    	state.output.print("popsize: "+Integer.toString(int_param)+"\n", infoLog);
    	doub_param = state.parameters.getDouble(par_mutProb, null);
    	state.output.print("mutProb: " + Double.toString(doub_param)+"\n", infoLog);
    	str_param = state.parameters.getString(par_mutType, null);
    	state.output.print("mutType: "+str_param+"\n", infoLog);
    	doub_param = state.parameters.getDouble(par_mutStDev, null);
    	state.output.print("mutStDev: "+ Double.toString(doub_param) +"\n", infoLog);
    	
    	state.output.print("fin run info\n", infoLog);

    	
    }
    
    public void finalStatistics(final EvolutionState state, final int result)
    {
    super.finalStatistics(state,result);
    
    int capacity = state.simulations[0].getCapacity();
    int NumPatients = state.simulations[0].getNumPatients();
    int weeks = state.simulations[0].getweeks();
    double SEVERITY_ALLOCATION = state.simulations[0].getSEVERITY_ALLOCATION();
    double CONTINUITY_ALLOCATION = state.simulations[0].getCONTINUITY_ALLOCATION();
    double DISEASE_SEVERITY = state.simulations[0].getDISEASE_SEVERITY();
    double LEARNING_RATE = state.simulations[0].getLEARNING_RATE();
    double SUBJECTIVE_INITIATIVE = state.simulations[0].getSUBJECTIVE_INITIATIVE();
    double EXP_POS = state.simulations[0].getEXP_POS();    
    double EXP_NEG = state.simulations[0].getEXP_NEG();
    double t = state.simulations[0].gett();
    

    state.output.println("capacity " + Integer.toString(capacity), careLog);   
    state.output.println("NumPatients " + Integer.toString(NumPatients), careLog);   
    state.output.println("weeks " + Integer.toString(weeks), careLog);   
    state.output.println("SEVERITY_ALLOCATION " + Double.toString(SEVERITY_ALLOCATION), careLog);   
    state.output.println("CONTINUITY_ALLOCATION " + Double.toString(CONTINUITY_ALLOCATION), careLog);   
    state.output.println("DISEASE_SEVERITY " + Double.toString(DISEASE_SEVERITY), careLog);   
    state.output.println("LEARNING_RATE " + Double.toString(LEARNING_RATE), careLog);   
    state.output.println("SUBJECTIVE_INITIATIVE " + Double.toString(SUBJECTIVE_INITIATIVE), careLog);   
    state.output.println("EXP_POS " + Double.toString(EXP_POS), careLog);   
    state.output.println("EXP_NEG " + Double.toString(EXP_NEG), careLog);   
    state.output.println("t " + Double.toString(t), careLog); 
    state.output.println("fit: doctor.provided_treatment", careLog);

    
    }
    
}
