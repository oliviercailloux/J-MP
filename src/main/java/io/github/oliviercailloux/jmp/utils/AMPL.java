package io.github.oliviercailloux.jmp.utils;

import java.util.List;



import io.github.oliviercailloux.jlp.elements.ComparisonOperator;
import io.github.oliviercailloux.jlp.elements.Constraint;
import io.github.oliviercailloux.jlp.elements.SumTerms;
import io.github.oliviercailloux.jlp.elements.Variable;

import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jlp.mp.VariablesInMP;



public class AMPL{
	
	    private MPBuilder  mp;
	    
	    
	    public AMPL(MPBuilder  mp){
	    	this.mp=mp;
	    }
	    
	    
	 public String getAMPL (){
		
		
			
			//Now we transform problem to AMPL
			
		String	amplProgram="";
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
			
			
			return amplProgram;
			
			
			
			
	
			
	 
	    }
	 
	 
	 
	//this method transform SumTearms to linear description
		private  String sumTermsToDescription(SumTerms termsTxt) {
			
			String objtxt=Double.toString(termsTxt.get(0).getCoefficient())+" * ";
			objtxt+=termsTxt.get(0).getVariable().getName();
			for(int j=1;j<termsTxt.size();j++)
			{
				objtxt+=" + "+Double.toString(termsTxt.get(j).getCoefficient())+" * "+termsTxt.get(j).getVariable().getName();
			}
			
			return objtxt;
		}
		
		//This method will transform a constraint to textual description
		
		private  String constraintToDescription(Constraint c){
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
		private  String contraintsToDescription(List<Constraint> cs){
			String result="";
			for(int k=0;k<cs.size()-1;k++)
				result=result+constraintToDescription(cs.get(k))+'\n';
			result=result+constraintToDescription(cs.get(cs.size()-1));
			return result;
		}
		
		//This method will transform bounds of variable to textual description
		private  String boundsToDescrition(Variable v){
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
		private  String boundsOfAllToDescription(List<Variable> vs){
			String result="";
			for(int p=0;p<vs.size()-1;p++)
				result+="subject to "+vs.get(p).getName()+"_limit: "+boundsToDescrition(vs.get(p))+'\n';
			result+="subject to "+vs.get(vs.size()-1).getName()+"_limit: "+boundsToDescrition(vs.get(vs.size()-1));
			return result;
		}
		
		
		
}
