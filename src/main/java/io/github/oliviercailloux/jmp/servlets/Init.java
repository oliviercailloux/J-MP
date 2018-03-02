package io.github.oliviercailloux.jmp.servlets;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jmp.utils.FakeDB;

import java.io.IOException;



import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/initProblems")
public class Init extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FakeDB db;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	 
            throws ServletException, IOException {
		
		//Initializing the databse with two problems
		 Variable xb=Variable.of("XB", INT_DOMAIN, FiniteRange.closed(0, 6000), "Bands");
	        Variable xc=Variable.of("XC", INT_DOMAIN, FiniteRange.closed(0, 4000), "coils");
	        SumTerms terms=SumTerms.of(25, xb, 30, xc);
	        Objective objFunction=Objective.max(terms);
	        String description="(hours to make a ton of bands) × XB + (hours to make a ton of coils) × XCThis number cannot exceed the 40 hours available";
	        Constraint c1=Constraint.of(description, SumTerms.of((double)1/200, xb,(double)1/400, xc), ComparisonOperator.LE, 40);
	       
	        MPBuilder mp1=MP.builder();
	        
	        
	        mp1.setName("Prod0");
	        mp1.setObjective(objFunction);
	        mp1.getConstraints().add(c1);
	       db.addPM(1, mp1);
	       
	       MPBuilder problem = MP.builder();
			problem.setName("OneFourThree");
			 Variable x = Variable.integer("x");
			 Variable y = Variable.integer("y");
			problem.getVariables().add(x);
			problem.getVariables().add(y);

			problem.setObjective(Objective.max(SumTerms.of(143, x, 60, y)));
			problem.getConstraints().add(Constraint.of("c1", SumTerms.of(120, x, 210, y), ComparisonOperator.LE, 15000));
			problem.getConstraints().add(Constraint.of("c2", SumTerms.of(110, x, 30, y), ComparisonOperator.LE, 4000));
			problem.getConstraints().add(Constraint.of("c3", SumTerms.of(1, x, 1, y), ComparisonOperator.LE, 75));
			db.addPM(2, problem);
			System.out.println("database created");
	       
	}

}
