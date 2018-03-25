package io.github.oliviercailloux.jmp.utils;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.google.common.collect.Range;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Term;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.IMP;

public class MPToJSON {

	/**
	 * Transform the problem to json object
	 * 
	 * @param mp
	 * @return json object
	 */
	public JsonObject getJson(IMP mp) {

		JsonObject model = Json.createObjectBuilder()
				.add("ProblemName", mp.getName())
				.add("Objective", objectiveToJson(mp.getObjective()))
				.add("Constraints", constrantsToJson(mp.getConstraints()))
				.build();

		return model;
	}

	private JsonArray constrantsToJson(List<Constraint> cs) {
		JsonArrayBuilder array = Json.createArrayBuilder();
		for (int i = 0; i < cs.size(); i++)
			array.add(constraintToJson(cs.get(i)));
		return array.build();
	}

	private JsonObject constraintToJson(Constraint c) {
		JsonObjectBuilder ob = Json.createObjectBuilder();
		ob.add("Lhs", sumTermsToJson(c.getLhs()))
				.add("ComparisonOperator",
						comparaisonOpToString(c.getOperator()))
				.add("Rhs", c.getRhs()).add("Description", c.getDescription());
		return ob.build();
	}

	private String comparaisonOpToString(ComparisonOperator op) {
		String result = "";
		if (op.equals(ComparisonOperator.LE))
			result += "<=";
		if (op.equals(ComparisonOperator.GE))
			result += ">=";
		if (op.equals(ComparisonOperator.EQ))
			result += "=";
		return result;
	}

	private JsonObject objectiveToJson(Objective f) {
		JsonObjectBuilder ob = Json.createObjectBuilder();
		if (f.getSense().toString().equals("MAX"))
			ob.add("Sense", "MAX");
		else
			ob.add("Sense", "MIN");
		ob.add("Function", sumTermsToJson(f.getFunction()));
		return ob.build();
	}

	private JsonArray sumTermsToJson(SumTerms terms) {
		JsonArrayBuilder array = Json.createArrayBuilder();
		for (int i = 0; i < terms.size(); i++)
			array.add(termToJson(terms.get(i)));
		return array.build();
	}

	private JsonObject termToJson(Term a) {
		JsonObjectBuilder ob = Json.createObjectBuilder();
		ob.add("Coefficient", a.getCoefficient()).add("Variable",
				variableToJson(a.getVariable()));
		return ob.build();
	}

	private JsonObject variableToJson(Variable x) {
		JsonObject model = Json.createObjectBuilder().add("Name", x.getName())
				.add("VariableDomain", x.getDomain().toString())
				.add("Range", rangeToJson(x.getBounds())).build();
		return model;
	}

	private JsonObject rangeToJson(Range<Double> r) {
		JsonObjectBuilder ob = Json.createObjectBuilder();
		if (r.hasLowerBound())
			ob.add("LowerBound", Double.toString(r.lowerEndpoint()));
		else
			ob.add("LowerBound", "NegativeInfinity");
		if (r.hasUpperBound())
			ob.add("UpperBound", Double.toString(r.upperEndpoint()));
		else
			ob.add("UpperBound", "PositiveInfinity");
		JsonObject model = ob.build();
		return model;

	}

}
