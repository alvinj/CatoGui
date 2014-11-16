package **PACKAGE_NAME**.controller;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import **PACKAGE_NAME**.container.*;
import **PACKAGE_NAME**.model.*;

import com.devdaily.web.RequestParameter;


public class **CLASS_NAME**Controller extends HttpServlet
{

  /**
   * doGet()
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {
    doPost(request, response);
  }

  /**
   * doPost()
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException
  {

    HttpSession session = request.getSession(false);

    // if the session is invalid from here on, there is a problem...
    if (session == null)
    {
      request.setAttribute("ErrorMsg", "Error -- the session has expired.");
      ControllerUtil.forwardTo(this, request, response, Controller.ERROR_PAGE);
      return;
    }

    String action = request.getAttribute("ACTION");
    if ( action.equals("ADD") )
    {
      doAdd(request,response);
    }

  }


  /**
   * doAdd() -- use the information in the request to create a **CLASS_NAME**
   *            object and insert it into the database.
   */
  public void doAdd(HttpServletRequest request, HttpServletResponse response)
  {
    **CLASS_NAME** **CLASS_INSTANCE_NAME** = new **CLASS_NAME**();

    **FOREACH_FIELD**
    **CLASS_INSTANCE_NAME**.set**FIELD_LABEL**( RequestParameter.get**FIELD_TYPE_FIRST_CHAR_UPPER**( (String)request.getAttribute("**FIELD_NAME**") );
    **END_FOREACH_FIELD**

    **CLASS_NAME**Container **CLASS_INSTANCE_NAME**Container = new **CLASS_NAME**Container();
    try
    {
      **CLASS_INSTANCE_NAME**Container.insert( **CLASS_INSTANCE_NAME** );
    }
    catch (SQLException se)
    {
      // what to do with this?
    }

  }



}