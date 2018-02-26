import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jmp.utils.AMPL;

import org.junit.Test;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;
import static org.junit.Assert.assertEquals;
public class TestAMPL {

	
	private static final String FILENAME = "ampl.txt";
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
       //Reading the ampl format of this problem
         String output="";
     	output = readAmplSyntax(output);
		
         assertEquals(output,ampl.getAMPL());
    }

	private String readAmplSyntax(String output) {
		BufferedReader br = null;
		FileReader fr = null;

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			
			fr = new FileReader(classLoader.getResource(FILENAME).getFile());
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				output+=sCurrentLine+"\n";
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		output=output.trim();
		return output;
	}
}
