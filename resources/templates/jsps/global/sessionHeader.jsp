<%@ page %>

<%--  include this page into every other page to properly initialize the session object
      and protect us from people that might try to abuse the system or otherwise
      attack us. --%>

<%
    if (session == null)
    {
      %>
      <jsp:forward page="login.jsp" />
      <%
    }

    // check our secret session param (userWentThroughLogin).
    // if the user went through the login page this variable will be set.
    // if they did not go through the login page this variable will not be set.
    // took this approach because I was not having success managing sessions the way I wanted to.

    String userWentThroughLogin = (String)session.getValue("userWentThroughLogin");
    if ( userWentThroughLogin == null )
    {
			session.putValue("loginfailed", "true");
      %>
      <jsp:forward page="login.jsp" />
      <%
    }

    //response.setHeader("Pragma", "No-cache");
    //response.setHeader("Cache-Control", "no-cache");
    //response.setDateHeader("Expires",0);

%>

