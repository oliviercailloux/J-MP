package io.github.oliviercailloux.jmp.servlets;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import io.github.oliviercailloux.jlp.mp.MPBuilder;
import io.github.oliviercailloux.jmp.utils.AMPL;
import io.github.oliviercailloux.jmp.utils.FakeDB;
import io.github.oliviercailloux.jmp.utils.MPToJSON;

@Path("/{ProblemID}")
public class GetProblem {

	@Inject
	private FakeDB db;

	private Map<Integer, MPBuilder> listProblem;

	private MPBuilder mp;

	@GET
	@Path("/AMPL")
	@Produces("text/html")
	public String getAMPL(@PathParam("ProblemID") String problemId) {

		int id = Integer.parseInt(problemId);
		listProblem = db.getProblemList();
		if (listProblem.size() == 0 || !listProblem.containsKey(id)) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {

			mp = listProblem.get(id);
			AMPL ampl = new AMPL();
			return ampl.getAMPL(mp);

		}

	}

	@GET
	@Path("/Json")
	@Produces("text/html")
	public String getJSON(@PathParam("ProblemID") String problemId) {
		int id = Integer.parseInt(problemId);
		listProblem = db.getProblemList();
		if (listProblem.size() == 0 || !listProblem.containsKey(id)) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} else {

			mp = listProblem.get(id);
			MPToJSON mpJson = new MPToJSON();
			return "JsonObject:\n" + mpJson.getJson(mp);
		}

	}

}
