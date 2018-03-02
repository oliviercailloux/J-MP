package io.github.oliviercailloux.jmp.servlets;

import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jmp.utils.AMPL;
import io.github.oliviercailloux.jmp.utils.FakeDB;
import io.github.oliviercailloux.jmp.utils.MPToJSON;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


@Path("/{ProblemID}")
public class GetProblem {
	
	@Inject
	private FakeDB db;
    
    private Map<Integer, MPBuilder> listProblem;
    private MPBuilder  mp;
	
	@GET
	@Path("/AMPL")
	@Produces("text/html")
	public String getAMPL(@PathParam("ProblemID") String problemId) {
		listProblem=db.getProblemList();
		mp=listProblem.get(Integer.parseInt(problemId));
		AMPL ampl=new AMPL(mp);
	    return ampl.getAMPL();
	}
	
	@GET
	@Path("/Json")
	@Produces("text/html")
	public String getJSON(@PathParam("ProblemID") String problemId) {
		listProblem=db.getProblemList();
		mp=listProblem.get(Integer.parseInt(problemId));
		MPToJSON mpJson=new MPToJSON(mp);
	    return "JsonObject:\n"+mpJson.getJson();
	}

}
