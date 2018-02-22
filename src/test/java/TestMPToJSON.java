
import javax.json.JsonObject;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jmp.classes.MPToJSON;

import org.junit.Test;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;
import static org.junit.Assert.assertEquals;
public class TestMPToJSON {
	
	MPBuilder mp1=MP.builder();
    
    @Test
    public void testGetJson(){
    	 Variable xb=Variable.of("XB", INT_DOMAIN, FiniteRange.closed(0, 6000), "Bands");
         Variable xc=Variable.of("XC", INT_DOMAIN, FiniteRange.closed(0, 4000), "coils");
         SumTerms terms=SumTerms.of(25, xb, 30, xc);
         Objective objFunction=Objective.max(terms);
         String description="(hours to make a ton of bands) × XB + (hours to make a ton of coils) × XCThis number cannot exceed the 40 hours available";
         Constraint c1=Constraint.of(description, SumTerms.of((double)1/200, xb,(double)1/400, xc), ComparisonOperator.LE, 40);
         mp1.setName("Prod0");
         mp1.setObjective(objFunction);
         mp1.getConstraints().add(c1);
         MPToJSON mpToJson=new MPToJSON(mp1);
         JsonObject model=mpToJson.getJson();
         
       
 		String output="{\"ProblemName\":\"Prod0\",\"Objective\":{\"Sense\":\"MAX\",\"Function\":[{\"Coefficient\":25.0,\"Variable\":{\"Name\":\"XB\",\"VariableDomain\":\"INT_DOMAIN\",\"Range\":{\"LowerBound\":\"0.0\",\"UpperBound\":\"6000.0\"}}},{\"Coefficient\":30.0,\"Variable\":{\"Name\":\"XC\",\"VariableDomain\":\"INT_DOMAIN\",\"Range\":{\"LowerBound\":\"0.0\",\"UpperBound\":\"4000.0\"}}}]},\"Constraints\":[{\"Lhs\":[{\"Coefficient\":0.005,\"Variable\":{\"Name\":\"XB\",\"VariableDomain\":\"INT_DOMAIN\",\"Range\":{\"LowerBound\":\"0.0\",\"UpperBound\":\"6000.0\"}}},{\"Coefficient\":0.0025,\"Variable\":{\"Name\":\"XC\",\"VariableDomain\":\"INT_DOMAIN\",\"Range\":{\"LowerBound\":\"0.0\",\"UpperBound\":\"4000.0\"}}}],\"ComparisonOperator\":\"<=\",\"Rhs\":40.0,\"Description\":\"(hours to make a ton of bands) × XB + (hours to make a ton of coils) × XCThis number cannot exceed the 40 hours available\"}]}";

 		assertEquals(output,model.toString());
    }

}
