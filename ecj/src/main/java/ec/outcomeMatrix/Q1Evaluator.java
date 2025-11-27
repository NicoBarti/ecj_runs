package ec.outcomeMatrix;

import ec.EvolutionState;
import patientCare.Care;
import patientCare.PatientInitializer;
import patientCare.ProviderInitializer;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import ec.vector.DoubleVectorIndividual;

public class Q1Evaluator extends Problem implements SimpleProblemForm {
	   private static final long serialVersionUID = 1;
	   

		// ind is the individual to be evaluated.
		// We're given state and threadnum primarily so we
		// have access to a random number generator
		// (in the form:  state.random[threadnum] ) 
		// and to the output facility
	   
	    public void evaluate(final EvolutionState state,
	            final Individual ind,
	            final int subpopulation,
	            final int threadnum){
	    	Care sim1;
	    	Care sim2;
	        if (ind.evaluated) return;   //don't evaluate the individual if it's already evaluated
	        if (!(ind instanceof DoubleVectorIndividual))
	            state.output.fatal("Oh-Oh! No es clase DoubleVectorIndividual!!!",null);
	        
	        DoubleVectorIndividual ind2 = (DoubleVectorIndividual)ind;
	        if (!(ind2.fitness instanceof SimpleFitness))
	            state.output.fatal("Whoa!  It's not a SimpleFitness!!!",null);
	        
	        int varsigma = 100;
	        sim1 = parametrizeAndRunSimulation(ind, varsigma);
	        sim2 = parametrizeAndRunSimulation(ind, varsigma);

	        Parameter saveSeedParam = new Parameter(new String[] {"pop", "metric"});
	        String par = state.parameters.getString(saveSeedParam,saveSeedParam);
	        
	        //if(par.equals("ineffect")){System.out.println(par);}
	        
	        double metric;
	        switch (par) {
	        case "ineffect": // To find the most ineffective
	        	metric = innefect(sim1, sim2, varsigma);
	        	break;
	        case "inequal": // To find the most inequal
	        	metric = inequal(sim1, sim2, varsigma);
	        	break;
	        case "effect": // To find the most effect
	        	metric = effect(sim1, sim2, varsigma);
	        	break;
	        case "equal":
	        	metric = equal(sim1, sim2, varsigma);
	        	break;
	        case "ineffect_inequal":
	        	metric = innefect(sim1, sim2, varsigma) + inequal(sim1, sim2, varsigma);
	        	break;
	        case "ineffect_equal":
	        	metric = innefect(sim1, sim2, varsigma) + equal(sim1, sim2, varsigma);
	        	break;
	        case "inequal_effect":
	        	metric = inequal(sim1, sim2, varsigma) + effect(sim1, sim2, varsigma);
	        	break;
	        case "equal_effect":
	        	metric = effect(sim1, sim2, varsigma) + equal(sim1, sim2, varsigma);
	        	break;
	        case "Wineffect_inequal":
	        	metric = innefect(sim1, sim2, varsigma) + inequal(sim1, sim2, varsigma)*4;
	        	break;
	        case "Wineffect_equal":
	        	metric = innefect(sim1, sim2, varsigma) + equal(sim1, sim2, varsigma)*4;
	        	break;
	        case "Winequal_effect":
	        	metric = inequal(sim1, sim2, varsigma)*4 + effect(sim1, sim2, varsigma);
	        	break;
	        case "Wequal_effect":
	        	metric = effect(sim1, sim2, varsigma) + equal(sim1, sim2, varsigma)*4;
	        	break;
	        	
	        default:
	        	metric = 0;
	        	System.out.println("Remember t add the metric param. For instance: java ec.Evolve -file /Users/nicolasbarticevic/ecj/ecj/src/main/java/ec/outcomeMatrix/q1.params -p pop.metric=effect -p stat.file=$tryInlineFilename.stat");
	        }
	        //Just trying to avoid memory overflow
	        sim1.kill(); sim2.kill();
	        // To find the most iineficient and inequal system
	        //double SystemInEffectivennes = ((sim.observer.getMeanFinalH()*52)/(5*varsigma));
	        //double SystemInEquality = (sim.observer.getVarianceFinalH()/((5*varsigma/52)*(5*varsigma/52)));

	        //between 0 inclusive and infinity exclusive, 0 being worst and infinity being better than the best
	        ((SimpleFitness)ind2.fitness).setFitness(state, metric ,false);
	        ind2.evaluated = true;
	}
	    
	    private double innefect(Care sim1, Care sim2, int varsigma) {
	    	return ((sim1.observer.getMeanFinalH()*52)/(5*varsigma))/2 + ((sim2.observer.getMeanFinalH()*52)/(5*varsigma))/2;
	    }
	    
	    private double inequal(Care sim1, Care sim2, int varsigma) {
	    	return (sim1.observer.getVarianceFinalH()/((5*varsigma/52)*(5*varsigma/52)))/2 + (sim2.observer.getVarianceFinalH()/((5*varsigma/52)*(5*varsigma/52)))/2;
	    }
	    
	    private double effect(Care sim1, Care sim2, int varsigma) {
	    	return (1 - ((sim1.observer.getMeanFinalH()*52)/(5*varsigma)))/2 + (1 - ((sim2.observer.getMeanFinalH()*52)/(5*varsigma)))/2;
	    }
	    
	    private double equal(Care sim1, Care sim2, int varsigma) {
	    	return (1 - (sim1.observer.getVarianceFinalH()/((5*varsigma/52)*(5*varsigma/52))))/2 + (1 - (sim2.observer.getVarianceFinalH()/((5*varsigma/52)*(5*varsigma/52))))/2;
	    }
	    
	    
	    protected Care parametrizeAndRunSimulation(Individual ind, int varsigma) {
	    	Care sim;
	    	sim = new Care(System.currentTimeMillis());
	    	
	    	double[] genome = ((DoubleVectorIndividual)ind).genome;
	    	//fixed_lambda 0
	    	//fixed_tau 1
 	    	//fixed_kappa 2
	    	//fixed_rho 3
	    	//fixed_eta 4
	    	//fixed_capN 5
	    	//fixed_capE 6
	    	//fixed_psi 7
	    	//N 8
	    	//W 9
	    	//fixed_delta 10
	    	//totalCpacity 11
	    	//policy 12
	    	
			sim.setvarsigma(varsigma);
	    	
			sim.setN((int)genome[8]);
			sim.setW((int)genome[9]);
			sim.settotalCapacity((int)genome[11]);
			sim.setPi(getPolicy(genome[12]));
			
			sim.setPATIENT_INIT("default");
			sim.setPROVIDER_INIT("default");
			
			sim.pat_init = new PatientInitializer(sim, "applyFixed");
			sim.setPATIENT_INIT("applyFixed");
			sim.pat_init.fixed_delta = genome[10];
			sim.pat_init.fixed_capN = genome[5];
			sim.pat_init.fixed_rho = genome[3];
			sim.pat_init.fixed_eta = genome[4];
			sim.pat_init.fixed_kappa = (float)genome[2];
			sim.pat_init.fixed_capE = genome[6];
			sim.pat_init.fixed_psi = genome[7];
			
			sim.prov_init = new ProviderInitializer(sim, "applyFixed");
			sim.setPROVIDER_INIT("applyFixed");
			sim.prov_init.fixed_lambda = genome[0];
			sim.prov_init.fixed_tau = genome[1];
			
	    	sim.start();
	    	sim.startObserver(true, false, false, false, false, false, false, false, false);
	    
			do{
				if (!sim.schedule.step(sim)) {
					System.out.println("Unknown problem when calling schedule.step");
					break;}
			}
			while (sim.schedule.getSteps() < sim.getvarsigma());
			sim.finish();
			return sim;
	    }
	    
	    private String getPolicy(double gen) {
	    	if(gen<1) {return "basal";}
	    	if(gen<2) {return "H_segmented";}
	    	else {return "patient_centred";}
	    }
	   
}
