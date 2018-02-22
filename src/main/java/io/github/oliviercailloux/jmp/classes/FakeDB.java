package io.github.oliviercailloux.jmp.classes;

import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.FiniteRange;
import io.github.oliviercailloux.jlp.elements.Objective;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;
import io.github.oliviercailloux.jlp.mp.MPBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.oliviercailloux.jlp.elements.VariableDomain.INT_DOMAIN;

import javax.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class FakeDB{
	
private  Map<Integer,MPBuilder> problemList=new LinkedHashMap<>();

public FakeDB()
{init();
}


public Map<Integer, MPBuilder> getProblemList(){
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
       
        MPBuilder mp1=MP.builder();
        
        
        mp1.setName("Prod0");
        mp1.setObjective(objFunction);
        mp1.getConstraints().add(c1);
       
        problemList.put(1, mp1);
       
        

	
}


}