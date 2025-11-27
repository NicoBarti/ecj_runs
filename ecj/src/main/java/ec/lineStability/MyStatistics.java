package ec.lineStability;
import ec.EvolutionState;
import ec.Individual;
import ec.simple.SimpleProblemForm;
import ec.simple.SimpleStatistics;
import ec.util.Parameter;

public class MyStatistics extends SimpleStatistics {

	public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state,base);
        
        //Parameter seed = state.parameters.getString(new String[] {"pop", "subpop", Integer.toString(0), "checkpointSeed"}, new String[] {"pop", "subpop", Integer.toString(0), "checkpointSeed"});
        		
       // state.parameters.set(seed, Long.toString(finder.seedFound()));
        
	}
	
    public void postInitializationStatistics(final EvolutionState state)
    {
    super.postInitializationStatistics(state);

    Individual original = state.population.subpops.get(0).individuals.get(0);
    state.output.println("Original system:",statisticslog);
    original.printIndividualForHumans(state, statisticslog);
    state.output.println("Original fitness: " +original.fitness.fitnessToStringForHumans() ,statisticslog);
    
    }
}
