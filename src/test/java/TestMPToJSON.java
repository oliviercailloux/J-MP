
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.json.JsonObject;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jmp.utils.MPToJSON;

import org.junit.Test;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;
import static org.junit.Assert.assertEquals;
public class TestMPToJSON {
	private static final String FILENAME = "json.txt";
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
       //Reading the json format of this problem
         String output="";
     	output = readJsonSyntax(output);
       
 		 		assertEquals(output,model.toString());
    }
    
    private String readJsonSyntax(String output) {
		BufferedReader br = null;
		FileReader fr = null;

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			
			fr = new FileReader(classLoader.getResource(FILENAME).getFile());
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				output+=sCurrentLine;
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
