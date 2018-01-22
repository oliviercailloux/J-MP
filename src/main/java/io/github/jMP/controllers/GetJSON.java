package io.github.jMP.controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.spi.JsonProvider;
import javax.json.stream.JsonGenerator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;

import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.Json;


import java.util.List;

import io.github.jMP.dao.FakeDB;
import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.VariablesInMP;
import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;

@WebServlet("/getJSON")
public class GetJSON extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	    @Inject
		private FakeDB db;
	    
	    private Map<Integer,MP> listProblem;
	    private MP mp;
	    private JsonObject value;
	    private StringWriter stringWriter;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	 
	            throws ServletException, IOException {
		
		//This code get the id of the problem
	        StringBuilder sb = new StringBuilder();
	        BufferedReader br = request.getReader();
	        String str = null;
	        while ((str = br.readLine()) != null) {
	            sb.append(str);
	        }
	        JSONObject jObj = null;
			try {
				jObj = new JSONObject(sb.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String problemId = null;
			try {
				problemId = jObj.getString("ProblemID");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String amplProgram="Not Found";
			if(db!=null)
			{listProblem=db.getProblemList();System.out.println("ok");
			if(listProblem.containsKey(Integer.parseInt(problemId))){System.out.println("ok");
			mp=listProblem.get(Integer.parseInt(problemId));
			
			//we transform problem to JSON
			amplProgram = "";
		value = Json.createObjectBuilder()
				.add("ProbName", mp.getName().toString())
				.add("ObjecFuncType", mp.getObjective().getSense().toString())
				.add("ObjecFuncValue", Json.createObjectBuilder()
						.add("XB",mp.getObjective().getFunction().get(0).getCoefficient())
						.add("XC",mp.getObjective().getFunction().get(1).getCoefficient()))
				.add("Constraints", Json.createObjectBuilder()
						.add("Lhs", Json.createObjectBuilder()
							.add("FirstLhs", mp.getConstraints().get(0).getLhs().get(0).toString())
							.add("SecLhs", mp.getConstraints().get(0).getLhs().get(1).toString()))
						.add("Operator", mp.getConstraints().get(0).getOperator().toAsciiString())
						.add("Rhs", mp.getConstraints().get(0).getRhs()))
				.add("VarBounds", Json.createObjectBuilder()
						.add("XBBounds", mp.getVariables().get(0).getBounds().toString())
						.add("XCBounds", mp.getVariables().get(1).getBounds().toString())
								)
					.build();
			
			stringWriter = new StringWriter();
			JsonWriterFactory writerFactory = Json.createWriterFactory(ImmutableMap.of(JsonGenerator.PRETTY_PRINTING, true));
			try (JsonWriter jsonWriter = writerFactory.createWriter(stringWriter)) {
				jsonWriter.write(value);
			}
	
			amplProgram=stringWriter.toString();
			}}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(amplProgram);
	       // System.out.println(stringWriter);
			
			
	    }
	 

		




		

		
		
		
}
