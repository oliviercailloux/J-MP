package io.github.oliviercailloux.jmp.utils;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.json.JsonObject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.MPBuilder;

public class TestMPToJSON {
	private static final String FILENAME = "json.txt";

	private static final Logger LOGGER = LoggerFactory.getLogger(TestMPToJSON.class);

	MPBuilder mp1 = MP.builder();

	@Test
	public void testGetJson() {
		Variable xb = Variable.of("XB", INT_DOMAIN, FiniteRange.closed(0, 6000), "Bands");
		Variable xc = Variable.of("XC", INT_DOMAIN, FiniteRange.closed(0, 4000), "coils");
		SumTerms terms = SumTerms.of(25, xb, 30, xc);
		Objective objFunction = Objective.max(terms);
		String description = "(hours to make a ton of bands) × XB + (hours to make a ton of coils) × XCThis number cannot exceed the 40 hours available";
		Constraint c1 = Constraint.of(description, SumTerms.of((double) 1 / 200, xb, (double) 1 / 400, xc),
				ComparisonOperator.LE, 40);
		mp1.setName("Prod0");
		mp1.setObjective(objFunction);
		mp1.getConstraints().add(c1);
		MPToJSON mpToJson = new MPToJSON();
		JsonObject model = mpToJson.getJson(mp1);
		LOGGER.info("Reading the json format of the problem from ressource file");
		String output = "";
		output = readJsonSyntax(output);

		assertEquals(output, model.toString());
	}

	private String readJsonSyntax(String output) {
		BufferedReader br = null;
		FileReader fr = null;

		try {

			URL resourceUrl = TestAMPL.class.getResource(FILENAME);

			br = new BufferedReader(new InputStreamReader(resourceUrl.openStream(), "UTF8"));

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				output += sCurrentLine;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null) {
					br.close();
				}

				if (fr != null) {
					fr.close();
				}

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		output = output.trim();
		return output;
	}

}
