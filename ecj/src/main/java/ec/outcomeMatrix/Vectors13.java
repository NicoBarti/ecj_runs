package ec.outcomeMatrix;
import ec.EvolutionState;
import ec.util.Code;
import ec.vector.DoubleVectorIndividual;

public class Vectors13 extends DoubleVectorIndividual {

	
    public String genotypeToStringForHumans()
    {
    StringBuilder s = new StringBuilder();
    for( int i = 0 ; i < genome.length ; i++ )
    	switch(i){
    	case 0:
    		s.append("fixed_lambda: "); s.append(t(genome[0])); 
    		break;
    	case 1:
    		s.append(" | fixed_tau: "); s.append(t(genome[1])); 
    		break;
    	case 2:
    		s.append(" | fixed_kappa: "); s.append(t(genome[2])); 
    		break;
    	case 3:
    		s.append(" | fixed_rho: "); s.append(t(genome[3])); 
    		break;
    	case 4:
    		s.append(" | fixed_eta: "); s.append(t(genome[4])); 
    		break;
    	case 5:
    		s.append(" | fixed_capN: "); s.append(t(genome[5])); 
    		break;
    	case 6:
    		s.append(" | fixed_capE: "); s.append(t(genome[6])); 
    		break;
    	case 7:
    		s.append(" | fixed_psi: "); s.append(t(genome[7])); 
    		break;
    	case 8:
    		s.append(" | N: "); s.append(t(genome[8])); 
    		break;
    	case 9:
    		s.append(" | W: "); s.append(t(genome[9])); 
    		break;
    	case 10:
    		s.append(" | fixed_delta: "); s.append(t(genome[10])); 
    		break;
    	case 11:
    		s.append(" | totalCapacity: "); s.append(t(genome[11])); 
    		break;
    	case 12:
    		s.append(" | policy: "); s.append(getPolicy(genome[12]));
    		break;
    		}
    	
    return s.toString();
    }
    
    private String t(double val) {
    	return String.format("%.4f", val);
    }
    
    private String getPolicy(double gen) {
    	if(gen<1) {return "basal";}
    	if(gen<2) {return "H_segmented";}
    	else {return "patient_centred";}
    }
	
	
}
