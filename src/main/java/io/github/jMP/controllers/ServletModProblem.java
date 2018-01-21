package io.github.jMP.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.VariablesInMP;
import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;

/**
 * Servlet implementation class ServletModProblem
 */
@WebServlet("/ServletModProblem")
public class ServletModProblem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModProblem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String choice = request.getParameter("submit");
		
		String idProblem = request.getParameter("problemid");
		
		if (choice.trim().equals("addconstraint")) 
		  {
			
			
		  }
		else if(choice.trim().equals("removeconstraint")) {
			
			
			
		}
else if(choice.trim().equals("setobjectivefunction")) {
			
	String objectivefunction = request.getParameter("objectivefunction");
	Objective obj = objectivefunction;
	//SumTerms terms=SumTerms.of(25, xb, 30, xc);
	//Objective objFunction=Objective.max(terms);
	MP mp=MP.create();
	mp.setObjective(objectivefunction);
	
	
		}
		
else{
	String varname = request.getParameter("namevar");
	double lowerbound = Double.parseDouble(request.getParameter("boundlower"));
	double higherbound = Double.parseDouble(request.getParameter("higherlower"));
}
	
	}

}
