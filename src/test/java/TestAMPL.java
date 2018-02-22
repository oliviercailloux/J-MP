import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jmp.classes.AMPL;

import org.junit.Test;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;
import static org.junit.Assert.assertEquals;
public class TestAMPL {

	
    
     MPBuilder mp1=MP.builder();
     
    @Test
    public void testGetAMPL(){
    	 Variable xb=Variable.of("XB", INT_DOMAIN, FiniteRange.closed(0, 6000), "Bands");
         Variable xc=Variable.of("XC", INT_DOMAIN, FiniteRange.closed(0, 4000), "coils");
         SumTerms terms=SumTerms.of(25, xb, 30, xc);
         Objective objFunction=Objective.max(terms);
         String description="(hours to make a ton of bands) × XB + (hours to make a ton of coils) × XCThis number cannot exceed the 40 hours available";
         Constraint c1=Constraint.of(description, SumTerms.of((double)1/200, xb,(double)1/400, xc), ComparisonOperator.LE, 40);
         mp1.setName("Prod0");
         mp1.setObjective(objFunction);
         mp1.getConstraints().add(c1);
         AMPL ampl=new AMPL(mp1);
         String output="var XB;"+"\n"+"var XC;"+"\n";
         output+="maximize Profit: 25.0 * XB + 30.0 * XC;"+"\n";
         output+="subject to Time: 0.005 * XB + 0.0025 * XC <= 40.0;"+"\n";
         output+="subject to XB_limit: 0.0 <= XB <= 6000.0;"+"\n";
         output+="subject to XC_limit: 0.0 <= XC <= 4000.0;";
         
         assertEquals(output,ampl.getAMPL());
    }
}
