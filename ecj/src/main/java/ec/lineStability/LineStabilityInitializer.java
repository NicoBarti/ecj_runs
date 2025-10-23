package ec.lineStability;

import ec.Initializer;
import ec.util.Parameter;
import ec.EvolutionState;
import ec.util.Parameter;
import ec.Population;
import ec.Subpopulation;
import ec.simple.SimpleFitness;
import ec.Individual;
import ec.vector.DoubleVectorIndividual;
import ec.simple.SimpleFitness;
import java.util.ArrayList;
import java.util.HashMap;
import stubbornLines.StubbornSystems;


public class LineStabilityInitializer extends Initializer{
	   private static final long serialVersionUID = 1;

	    public void setup(final EvolutionState state, final Parameter base)
	        { 
	        }

	    /** This is an almost copy of the original SimpleInitializer
	     * Here I run its same initialization routine, but I re-populate the vectors
	     * with the candidate from FindSubbornLine class */

	    public Population initialPopulation(final EvolutionState state, int thread)
	        {
	    	int subpop_n = 0;
	        Population p = setupPopulation(state, thread); 
	        p.populate(state, thread);
	        ArrayList<Individual>  inds = p.subpops.get(subpop_n).individuals; // for only 1 subpopulation
	        
	        StubbornSystems finder = new StubbornSystems("/Users/nicolasbarticevic/Desktop/simulationOutputs/checkpoints");
	        double[] vectrorizedParams = finder.rawFoundParams();
	        
	        DoubleVectorIndividual thisInd = null;
	        for(int i=0; i< inds.size(); i++) {
	        	thisInd = (DoubleVectorIndividual)inds.get(i);
	        	thisInd.genome = vectrorizedParams;
	        	((SimpleFitness)thisInd.fitness).setFitness(state, finder.foundFit(), false);
	        	
	        }
	        Parameter saveSeedParam = new Parameter(new String[] {"pop", "subpop", Integer.toString(subpop_n), "checkpointSeed"});
	        state.parameters.set(saveSeedParam, Long.toString(finder.seedFound()));
	        //state.foundSeed = finder.seedFound();
	        //state.setfoundSeed(finder.seedFound());
	        return p;
	        }
	                
	    public Population setupPopulation(final EvolutionState state, int thread)
	        {
	        Parameter base = new Parameter(P_POP);
	        Population p = (Population) state.parameters.getInstanceForParameterEq(base,null,Population.class);  // Population.class is fine
	        p.setup(state,base);
	        return p;
	        }
}
