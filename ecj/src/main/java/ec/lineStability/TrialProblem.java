package ec.lineStability;
import ec.*;
import ec.util.*;
import ec.simple.*;
import ec.vector.*;
import stubbornLines.experimentWithProvidedLines;
import patientCare.Care;
public class TrialProblem extends Problem implements SimpleProblemForm { 
	
	// ind is the individual to be evaluated.
	// We're given state and threadnum primarily so we
	// have access to a random number generator
	// (in the form:  state.random[threadnum] ) 
	// and to the output facility
	
	experimentWithProvidedLines experimenter = new experimentWithProvidedLines();
	Care sim;
	
    public void setup(final EvolutionState state, final Parameter base) 
    {
        //Parameter saveSeedParam = new Parameter(new String[] {"pop", "subpop", Integer.toString(0), "checkpointSeed"});
        experimenter.setcheckpoint_path("/Users/nicolasbarticevic/Desktop/simulationOutputs/checkpoints");
    }
	
	
    public void evaluate(final EvolutionState state,
            final Individual ind,
            final int subpopulation,
            final int threadnum){
        if (ind.evaluated) return;   //don't evaluate the individual if it's already evaluated
        if (!(ind instanceof DoubleVectorIndividual))
            state.output.fatal("Oh-Oh! No es clase DoubleVectorIndividual!!!",null);
        
        DoubleVectorIndividual ind2 = (DoubleVectorIndividual)ind;
        if (!(ind2.fitness instanceof SimpleFitness))
            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
        
        Parameter saveSeedParam = new Parameter(new String[] {"pop", "subpop", Integer.toString(0), "checkpointSeed"});
        sim = experimenter.tweakTheLine(state.parameters.getString(saveSeedParam,saveSeedParam), 500, (double[])(ind2.getGenome()));
        
        ((SimpleFitness)ind2.fitness).setFitness(state, sim.observer.getMeanFinalH()*-1,false);
        ind2.evaluated = true;
}
    
}
