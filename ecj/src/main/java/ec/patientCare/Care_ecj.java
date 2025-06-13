package ec.patientCare;
import ec.*;
import ec.util.*;
import ec.simple.*;
import ec.vector.*;


public class Care_ecj extends Problem implements SimpleProblemForm {

	// ind is the individual to be evaluated.
	// We're given state and threadnum primarily so we
	// have access to a random number generator
	// (in the form:  state.random[threadnum] ) 
	// and to the output facility

		Care simulation;
	
	    public void evaluate(final EvolutionState state,
	                         final Individual ind,
	                         final int subpopulation,
	                         final int threadnum)
	        {
	        if (ind.evaluated) return;   //don't evaluate the individual if it's already evaluated
	
	        if (!(ind instanceof IntegerVectorIndividual))
	            state.output.fatal("Oh-Oh! No es clase IntegerVectorIndividual!!!",null);
	        IntegerVectorIndividual ind2 = (IntegerVectorIndividual)ind;
	        
	        double f;
	        f = fit((int)ind2.genome[0]);
	        //System.out.println("Evaluando capacidad:" + ind2.genome[0]+ " fit:"+f);

	        
	        if (!(ind2.fitness instanceof SimpleFitness))
	            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);

	        ((SimpleFitness)ind2.fitness).setFitness(state,
	                // ...the fitness...
	                f,
	                ///... is the individual ideal?  Indicate here...
	                f == 0);
	        ind2.evaluated = true;
	        
	        }
	    
		public double fit(int capacity) {
			simulation = new Care(System.currentTimeMillis());
			simulation.setCapacity(capacity);	
			simulation.start();
			do{
				if (!simulation.schedule.step(simulation)) {
					System.out.println("algo falso en schedule.step"); //here return somethin to make it fatal
					break;}
			}
			while (simulation.schedule.getSteps() < simulation.getweeks());
			
			
			//int[][] distro = new int[patients.numObjs][weeks+1];
			int attempts = 0;
			for (int i = 0; i < simulation.patients.numObjs; i++) {
				int[] data = ((Patient) (simulation.patients.objs[i])).getB();
				for (int ii = 0; ii < simulation.weeks+1; ii++) {
					attempts = attempts + data[ii];
				}
			}
			// this is the fit to be evaluated with simplefitness
			return -attempts/simulation.patients.numObjs;
		}
	    
}
