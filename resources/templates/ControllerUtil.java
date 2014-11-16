package **PACKAGE_NAME**.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import **PACKAGE_NAME**.model.*;


public class ControllerUtil
{

  /**
   * Make it easy to forward to a given JSP.
   */
  public static void forwardTo( HttpServlet servlet, HttpServletRequest request, HttpServletResponse response, String page )
  throws ServletException, IOException
  {
    forwardTo(servlet.getServletContext(), request, response, page );
  }


  /**
   * Make it easy to forward to a given JSP.
   */
  public static void forwardTo( ServletContext context, HttpServletRequest request, HttpServletResponse response, String page )
  throws ServletException, IOException
  {
    context.getRequestDispatcher("/"+page).forward(request, response);
  }


  /**
   * Make it easy to determine whether the given request came from the specified page.
   */
  public static boolean requestFrom( HttpServletRequest request, String page )
  {
    String currentPage = request.getParameter("CURRENT_PAGE");
    if ( currentPage == null )
    {
      return false;
    }
    return currentPage.equals(page);
  }


  public static boolean nullOrEmpty(String toTest)
  {
    return (toTest == null) || (toTest.trim().equals(""));
  }


}