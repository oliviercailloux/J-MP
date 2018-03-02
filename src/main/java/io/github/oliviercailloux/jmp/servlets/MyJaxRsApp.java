package io.github.oliviercailloux.jmp.servlets;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("problems")
public class MyJaxRsApp extends Application {
	/** Empty. The server will then discover all resource classes automatically. */
}