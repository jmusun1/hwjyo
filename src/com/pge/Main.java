package com.pge;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.nio.charset.Charset;

public class Main extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    //resp.getWriter().print("Hello from Java! Charset: " + Charset.defaultCharset());
	     RequestDispatcher view = req.getRequestDispatcher("/html/index.html");
	        // don't add your web-app name to the path

	        view.forward(req, resp);   }
}
