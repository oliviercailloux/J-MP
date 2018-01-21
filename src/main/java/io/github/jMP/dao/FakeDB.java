package io.github.jMP.dao;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;
import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class FakeDB{
	
private  Map<Integer,MP> problemList=new LinkedHashMap<>();

public FakeDB()
{init();
}


public Map<Integer, MP> getProblemList(){
	return problemList;
}



public  void init()
{
	


       
        Variable xb=Variable.of("XB", INT_DOMAIN, FiniteRange.closed(0, 6000), "Bands");
        Variable xc=Variable.of("XC", INT_DOMAIN, FiniteRange.closed(0, 4000), "coils");
        SumTerms terms=SumTerms.of(25, xb, 30, xc);
        Objective objFunction=Objective.max(terms);
        String description="(hours to make a ton of bands) × XB + (hours to make a ton of coils) × XCThis number cannot exceed the 40 hours available";
        Constraint c1=Constraint.of(description, SumTerms.of((double)1/200, xb,(double)1/400, xc), ComparisonOperator.LE, 40);
        MP mp1=MP.create();
        mp1.setName("Prod0");
        mp1.setObjective(objFunction);
        mp1.add(c1);
        mp1.getVariable(description);
        problemList.put(1, mp1);
       
        Variable xCin=Variable.of("X", INT_DOMAIN, FiniteRange.closed(0, 3), "cinemaSceances");
        Variable xSwe=Variable.of("Y", INT_DOMAIN, FiniteRange.closed(0, 15), "sweets");
        SumTerms terms2=SumTerms.of(30, xCin, 1, xSwe);
        Objective objFunction2=Objective.max(terms2);
        String description2="(price of a sceance at local cinema) × xCin + (price of a sweet at grocery) × xSweThis number cannot exceed the 5 euros that parents gave me";
        Constraint c2=Constraint.of(description, SumTerms.of((double)5/2, xCin,(double)1/10, xSwe), ComparisonOperator.LE, 5);
        MP mp2 = MP.create();
        mp2.setName("StreetPb0");
        mp2.setObjective(objFunction2);
        mp2.add(c2);
        mp2.getVariable(description2);
        problemList.put(2, mp2);


	
}


}