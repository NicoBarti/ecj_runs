package ec.patientCare;
import ec.*;
import ec.util.*;

public class MiEvoutionState extends ec.simple.SimpleEvolutionState {
	


	public void setup(EvolutionState state, Parameter base)
	{
	super.setup(state, base); // state is obviously the MyEvolutionState itself
	// there are two cases where we’d want to set this up:
	// 1. I am running ECJ in a single process, and am NOT running with
	// distributed (master/slave) evaluation
	
	simulations = new Care[state.evalthreads];
	for(int i = 0; i <state.evalthreads; i++) {
		simulations[i] = new Care(state.random[i].nextLong());
	}

	
	// 2. I am running with distributed evaluation, and I am a slave (not the master).
	// So we check for those cases here:
	// the code below verifies that I’m running on a slave, not the master
	// ec.util.Parameter param = new ec.util.Parameter("eval.i-am-slave");
	// boolean amASlave = state.parameters.getBoolean(param, null, false);

	// the code below verifies that I’m doing distributed evaluation
	//boolean doingDistributedEvaluation = (Evaluator.masterproblem == null);
	// okay here we go
	//if (!doingDistributedEvaluation || amASlave)
	
	//{
	//	
	//simulations = new Simulation[state.evalthreads];
	// do your library setup here
	// now create the simulation array
	}
	
}
