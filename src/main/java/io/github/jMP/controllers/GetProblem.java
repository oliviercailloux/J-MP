package io.github.jMP.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.github.jMP.dao.FakeDB;
import io.github.oliviercailloux.jlp.elements.Variable;
import io.github.oliviercailloux.jlp.mp.MP;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/getProblem")
public class GetProblem extends HttpServlet {
	private static final long serialVersionUID = 2L;
	
	
	private Map<Integer,MP> listProblem;
	private MP mp;
	
    @Inject
	private FakeDB db;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	 
            throws ServletException, IOException {
    	
    	List<Variable> listVariables=null;
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
			String descriptionProgram="Not Found";
			if(db!=null)
			{listProblem=db.getProblemList();System.out.println("ok");
			if(listProblem.containsKey(Integer.parseInt(problemId))){System.out.println("ok");
			mp=listProblem.get(Integer.parseInt(problemId));
			
			//GET PROBLEM DESCRIPTION
			//We get problem informations and store them in a string
			descriptionProgram="********* Problem name : "+mp.getName()+" *******\n";
			
			//Add the variables
			descriptionProgram+="List of variables : \n";
			listVariables=mp.getVariables();
			for(int i=0;i<listVariables.size();i++){
				descriptionProgram+="* "+listVariables.get(i).getName();
				descriptionProgram+= " defined in the intervall " ; 
				descriptionProgram+= (listVariables.get(i).getBounds().toString());
				descriptionProgram+= "\n";
			}
			
			//Add the objective function
			descriptionProgram+="\n We want to output the ";
			descriptionProgram+= mp.getObjective().getSense().toString();
			descriptionProgram+= " of the following objective function: \n **"; 
			descriptionProgram+= mp.getObjective().getFunction().toString() + "\n";
			
			//Add the constraints descriptions
			descriptionProgram+= "Under the following constraints: \n";
			for(int i=0;i<  mp.getConstraints().size() ;i++){
				descriptionProgram +="* "+mp.getConstraints().get(i).getLhs().toString()+" "+ mp.getConstraints().get(i).getOperator().toString()+" "+ Double.toString(mp.getConstraints().get(i).getRhs())+"\n";
			}
			
			
			//Clean the name of variables
			for(int i=0;i<listVariables.size();i++){
				descriptionProgram = descriptionProgram.replace("_" + mp.getObjective().getFunction().getVariables().get(i).getReferences().toString().replace("[","").replace("]",""), ""); 
			}
		    descriptionProgram+= "\n***************** END ************";

			
			}}
			//This code return the description response		
			  
	        response.setContentType("text/plain");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(descriptionProgram);
    	
    }
    
    

}
