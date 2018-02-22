package io.github.oliviercailloux.jmp.servlets;

import io.github.oliviercailloux.jlp.mp.MPBuilder;

import io.github.oliviercailloux.jmp.classes.FakeDB;
import io.github.oliviercailloux.jmp.classes.MPToJSON;

import java.io.IOException;

import java.util.Map;


import javax.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getJSON")
public class GetJSON extends HttpServlet{
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private FakeDB db;
    
    private Map<Integer, MPBuilder> listProblem;
    private MPBuilder  mp;
    
    private MPToJSON mpJson;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	 
            throws ServletException, IOException {
    	db=new FakeDB();
    	
    	//This code get the id of the problem
    	String problemId = request.getParameter("ProblemID");
    	
		String jsonProgram="Not Found";
		if(db!=null)
		{listProblem=db.getProblemList();
		if(listProblem.containsKey(Integer.parseInt(problemId))){
		mp=listProblem.get(Integer.parseInt(problemId));
		
		//Now we transform problem to Json
		mpJson=new MPToJSON(mp);
		
		JsonObject model=mpJson.getJson();
		
		jsonProgram = "JsonObject:\n"+model.toString();
		
		
		
		}}
		//This code return the ampl response		
		  
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonProgram);
        
    }
}
