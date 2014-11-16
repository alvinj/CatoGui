<%@ page import="java.sql.*,java.util.*,com.devdaily.web.PagedList" errorPage="error.jsp" %>

<%-- GET THE SESSION FROM THE SESSION HEADER --%>
<!-- <%@ include file="global/sessionHeader.jsp" %> -->
<!-- <%@ include file="global/properties.jsp" %> -->

<%
   Connection conn      = (Connection)session.getValue("connection");
   String     errorMsg  = (String)session.getValue("ERROR_MSG");
   String     username  = (String)session.getValue("USERNAME");

   PagedList searchResults = (PagedList)session.getValue("SEARCH_RESULTS");

   // parameters that are passed to us for Previous/Next page support
   String doPrevious  = request.getParameter("PREVIOUS");       // will contain a value if PREV was pressed to get here
   String doNext      = request.getParameter("NEXT");           // will contain a value if NEXT was pressed to get here
   String prevPageStr = request.getParameter("PREVIOUS_PAGE");
   String nextPageStr = request.getParameter("NEXT_PAGE");
   int currentPage = 1;  // 1 by default
   if ( doPrevious != null )
   {
     currentPage = (new Integer(prevPageStr)).intValue();
   }
   if ( doNext != null )
   {
     currentPage = (new Integer(nextPageStr)).intValue();
   }
%>

<html>

<head>
<title>Search Results</title>

</head>

<body bgcolor="#ffffff">

<%-- HEADER STUFF STARTS --%>
<%-- HEADER STUFF STOPS --%>

    <%-- HERE IS THE MAIN TABLE --%>
    <div align="left">
    <table border="0" cellpadding="5" cellspacing="1" width="100%">
      <tr>
        <td bgcolor="#ffffff">&nbsp;</td>
      </tr>

      <tr>
        <!-- <th align="center" bgcolor="<%=TH_BGCOLOR%>"><%=TH_FONT_TAG%>Forum</font></th> -->
        **FOREACH_FIELD**
        <th align="center" bgcolor="<%=TH_BGCOLOR%>"><%=TH_FONT_TAG%>**FIELD_LABEL**</font></th>
        **END_FOREACH_FIELD**
      </tr>

      <%-- print one row for each topic --%>
      <%
      Vector searchResultsForThisPage = searchResults.getListForPage(currentPage);

      if ( searchResultsForThisPage == null  ||  searchResultsForThisPage.size() < 1 )
      {
        %>
        <tr>
          <td align="center" bgcolor="<%=TD_BGCOLOR_LIGHT%>">
            <%=TD_FONT_TAG%><br><font color=red>Sorry, no **CLASS_NAME** matched your search criteria.<br>&nbsp;</font></font>
          </td>
        </tr>
        <%
      }
      else
      {
        **CLASS_NAME** current**CLASS_NAME** = null;
        Iterator **CLASS_INSTANCE_NAME**Iterator = searchResults.iterator();
        while ( **CLASS_INSTANCE_NAME**Iterator.hasNext() )
        {
          current**CLASS_NAME** = **CLASS_INSTANCE_NAME**Iterator.next();
          %>
          <tr>
            **FOREACH_FIELD**
            <td align="left" bgcolor="<%=TD_BGCOLOR_LIGHT%>"><%=TD_FONT_TAG%><%=**CLASS_INSTANCE_NAME**.get**FIELD_NAME**()%></font></td>
            **END_FOREACH_FIELD**
          </tr>
          <%
        }
      }
      %>

    <%-- CREATE PREV/NEXT BUTTONS IF NEEDED --%>
    <% if ( searchResults.getCurrentPageNumber() > 1  ||  searchResults.hasNextPage() )   { %>
    <tr>
      <td colspan=4 align="center" bgcolor="white" valign="top">
        <form method="POST" action="searchResults.jsp">
        <input type="hidden" name="" value="">
          <% if ( searchResults.getCurrentPageNumber() > 1 )   { %>
              <input type="hidden" name="PREVIOUS_PAGE" value="<%=searchResults.getCurrentPageNumber()-1%>">
              <input type="submit" value="  << Previous  " name="PREVIOUS">
          <% } %>
          &nbsp;&nbsp;
          <% if ( searchResults.hasNextPage() )   { %>
              <input type="hidden" name="NEXT_PAGE" value="<%=searchResults.getCurrentPageNumber()+1%>">
              <input type="submit" value="  Next >>  " name="NEXT">
          <% } %>
        <br><%=TD_FONT_TAG%>page <%=currentPage%> of <%=searchResults.numPagesInList()%></font>
        </form>
      </td>
    </tr>
    <% } %>
    <%-- END PREV/NEXT BUTTONS --%>


    </table>
    </div>

    </td>
  </tr>
  <tr>
    <td width="100%"></td>
  </tr>
</table>
</center></div>

<p>&nbsp;</p>

<p>&nbsp;</p>
</body>
</html>
