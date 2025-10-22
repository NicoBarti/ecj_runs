package ec.patientCare_previous;
import ec.*;
import ec.util.*;
import ec.simple.*;
import ec.vector.*;

public class learningANDSubjective extends Problem implements SimpleProblemForm { 
	
	// ind is the individual to be evaluated.
	// We're given state and threadnum primarily so we
	// have access to a random number generator
	// (in the form:  state.random[threadnum] ) 
	// and to the output facility
	
	//Care simulation;
	
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
        
        
        double f = 0;
        int numberOfReps = 10;
        
        for(int i = 0; i < numberOfReps; i++) {
        	f += fit((DoubleVectorIndividual)ind2, state.simulations[threadnum]);
        }
        f = f/numberOfReps;
        
        ((SimpleFitness)ind2.fitness).setFitness(state,
                // ...the fitness...
                f,
                ///... is the individual ideal?  Indicate here...
                f == 0);
        ind2.evaluated = true;
}

    public double fit(DoubleVectorIndividual ind2, Care simulation) {
		simulation.start();
    	simulation.setLEARNING_RATE(ind2.genome[0]);
		simulation.setSUBJECTIVE_INITIATIVE(ind2.genome[1]);

		do{
			if (!simulation.schedule.step(simulation)) {
				System.out.println("algo falso en schedule.step"); //here return somethin to make it fatal
				break;}
		}
		while (simulation.schedule.getSteps() < simulation.getweeks());
    	
		//double fit = 0; 
//		for (int i = 0; i < simulation.patients.numObjs; i++) {
//			fit += ((Patient) (simulation.patients.objs[i])).getNecesidades();
//		}
//		return -fit/simulation.patients.numObjs;
		return simulation.doctor.provided_treatment;
    	
    }
    
}
