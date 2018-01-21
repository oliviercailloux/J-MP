package io.github.jMP.controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

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

@WebServlet("/getAMPL")
public class GetAMPL extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	    @Inject
		private FakeDB db;
	    
	    private Map<Integer,MP> listProblem;
	    private MP mp;
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
			
			//Now we transform problem to AMPL
			
			amplProgram="";
			//Declaring variables
		VariablesInMP listeVariable=mp.getVariables();
		for(int i=0;i<listeVariable.size();i++)	
			amplProgram+="var "+listeVariable.get(i).getName()+";\n";

		
		//Getting objectivre function
		String objCmd;
		if(mp.getObjective().getSense().toString().equals("MAX"))
			objCmd="maximize Profit: ";
		else
			objCmd="minimize Profit: ";
		//getting objective function expression
		String objectivreline = sumTermsToDescription(mp.getObjective().getFunction());
		objectivreline+=";";
		amplProgram+=objCmd+objectivreline+"\n";
		//System.out.println(objCmd+objectivreline);
		String cstCmd="subject to Time: ";
		String cstTxt=contraintsToDescription(mp.getConstraints())+";";
		amplProgram+=cstCmd+cstTxt+"\n";
		//System.out.println(cstCmd+cstTxt);
		amplProgram+=boundsOfAllToDescription(mp.getVariables());
			
			
			}}
			
			
			
			
	//This code return the ampl response		
	  
	        response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(amplProgram);
			
	 
	    }
	 
	//this method transform SumTearms to linear description
		private static String sumTermsToDescription(SumTerms termsTxt) {
			
			String objtxt=Double.toString(termsTxt.get(0).getCoefficient())+" * ";
			objtxt+=termsTxt.get(0).getVariable().getName();
			for(int j=1;j<termsTxt.size();j++)
			{
				objtxt+=" + "+Double.toString(termsTxt.get(j).getCoefficient())+" * "+termsTxt.get(j).getVariable().getName();
			}
			
			return objtxt;
		}
		
		//This method will transform a constraint to textual description
		
		private static String constraintToDescription(Constraint c){
			String result="";
			result+=sumTermsToDescription(c.getLhs());
			if(c.getOperator().equals(ComparisonOperator.LE))
				result+=" <= ";
			if(c.getOperator().equals(ComparisonOperator.GE))
				result+=" >= ";
			if(c.getOperator().equals(ComparisonOperator.EQ))
				result+=" = ";
			result+=Double.toString(c.getRhs());
			return result;
		}
		//This method will transform all constraint to textual description
		private static String contraintsToDescription(List<Constraint> cs){
			String result="";
			for(int k=0;k<cs.size()-1;k++)
				result=result+constraintToDescription(cs.get(k))+'\n';
			result=result+constraintToDescription(cs.get(cs.size()-1));
			return result;
		}
		
		//This method will transform bounds of variable to textual description
		private static String boundsToDescrition(Variable v){
			String result="";
			if(v.getBounds().hasLowerBound())
				result+=Double.toString(v.getBounds().lowerEndpoint())+" <= ";
			result+=v.getName();
			if(v.getBounds().hasUpperBound())
				result+=" <= "+Double.toString(v.getBounds().upperEndpoint());
			result+=";";
			return result;
		}
		
		//This method will transform all bounds of variables of the mp to textual description
		private static String boundsOfAllToDescription(List<Variable> vs){
			String result="";
			for(int p=0;p<vs.size();p++)
				result+="subject to "+vs.get(p).getName()+"_limit: "+boundsToDescrition(vs.get(p))+'\n';
			return result;
		}
		
		
		
}
