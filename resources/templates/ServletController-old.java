package **PACKAGE_NAME**.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class Controller extends HttpServlet
{

  // where to go when errors occur
  public static final String ERROR_PAGE = "error.jsp";


  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    doPost(request, response);
  }


  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    //
  }


  public void init(ServletConfig config)
  throws ServletException
  {
    //
  }


}
