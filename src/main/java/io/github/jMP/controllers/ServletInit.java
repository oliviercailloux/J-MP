package io.github.jMP.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@WebServlet("/")
public class ServletInit extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/ClientEdit.jsp");
          requestDispatcher.forward(req, resp);
		
		
		
		/*String homePage="/WEB-INF/views/ClientEdit.jsp";
		resp.sendRedirect(homePage);*/
	}

}
