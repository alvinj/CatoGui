<%@ page import="java.sql.*,java.util.*,com.devdaily.dg.*,com.devdaily.web.CachedList" errorPage="error.jsp" %>
<%--
    Called by search.jsp.
    Algorithm:
      1. Get the search parameters.
      2. Process/filter the parameters as needed.
      3. Do the database search, retrieving the messages that contain our search criteria.
      4. Forward to searchResults.jsp for the display of the results.
--%>

<%-- GET THE SESSION FROM THE SESSION HEADER --%>
<%@ include file="global/sessionHeader.jsp" %>
<%@ include file="global/properties.jsp" %>

<%
   Connection conn      = (Connection)session.getValue("connection");
   String     errorMsg  = (String)session.getValue("errorMsg");

   //-------------------------------
   // STEP 1: get the form variables
   //-------------------------------

   String searchFor      = request.getParameter("SEARCH_FOR");
   String forumIDString  = request.getParameter("SEARCH_FORUM");
   String match          = request.getParameter("MATCH");
   //String date           = request.getParameter("DATE");
   String searchUsername = request.getParameter("USERNAME");


   //--------------------------------------
   // STEP 2: process/filter the parameters
   //--------------------------------------

   if (searchFor == null)      searchFor = "";
   if (forumIDString == null)  forumIDString = "1";
   if (match == null)          match = "ALL";
   if (searchUsername == null) searchUsername = "";

   int forumIDInt = (new Integer(forumIDString)).intValue();

   // strip out wayward characters


   //-------------------------------
   // STEP 3: do the database search
   //-------------------------------

   // searchResults will contain a list of Messages that match the search criteria
   Vector searchResults = null;
   CachedList cachedResults = null;

   if ( match.equals("ALL") )
   {
     searchResults = MessageList.getMessages(conn,forumIDInt,searchFor,MessageList.AND_SEARCH);
     cachedResults = new CachedList(searchResults);
   }
   else if ( match.equals("ANY") )
   {
     searchResults = MessageList.getMessages(conn,forumIDInt,searchFor,MessageList.OR_SEARCH);
     cachedResults = new CachedList(searchResults);
   }

   if ( searchResults == null )
   {
     session.putValue("errorMsg", "Please specify one or more words to search for.");
     %>
     <jsp:forward page="search.jsp" />
     <%
	 }
	 else
	 {
     session.putValue("SEARCH_RESULTS", cachedResults);
	 }

%>

   <jsp:forward page="searchResults.jsp" />
