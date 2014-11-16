
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public abstract class **CONTROLLER_NAME** extends HttpServlet
{

  private static final ERROR_PAGE = "error.jsp";
  private static final LOGIN_PAGE = "login.jsp";
  private static final MAIN_MENU  = "mainMenu.jsp";

  Client client;


  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
  {
    doPost(request, response);
  }


  public void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException 
  {

    HttpSession session = request.getSession(false);
    if ( session == null )
    {
      ControllerUtil.forwardTo(this, request, response, ERROR_PAGE);
      return;
    }

    // determine if the client exists; if they don't, go back to the login page with an error
    // if they do, go on to the workspace

    if ( validateClient(request) )
    {
      ControllerUtil.forwardTo(this, request, response, MAIN_MENU);
    }
    else
    {
      ControllerUtil.forwardTo(this, request, response, LOGIN_PAGE);
      return;
    }

  }


  /**
   * Validate the client information from the request against the clients in the database.
   */
  public boolean validateClient (HttpServletRequest request)
  {
    client = new client();
    String username = request.getParameter( "USER_NAME" );
    String password = request.getParameter( "PASSWORD" );

    if ( (username == null) || (password == null) )
    {
      return false;
    }
    else
    {
      client.setUsername( username );
      client.setPassword( password );

      // get the info for this client from the data object; if the client does not
      // exist, a null will be returned
      client = ClientDO.select( client );

      if ( client == null )
      {
        return false;
      }
      else
      {
        return true;
      }

    }


}











