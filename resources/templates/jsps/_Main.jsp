<%@ page import="java.sql.*,java.util.*,com.devdaily.*,com.devdaily.todo.*,com.devdaily.memo.*" errorPage="error.jsp" %>

<html>

<%-- GET THE SESSION FROM THE SESSION HEADER --%>
<%@ include file="global/sessionHeader.jsp" %>
<% session.putValue( "currentAppName", "memos");  %>

<%
   Connection conn        = (Connection)session.getValue("connection");
   User       user        = (User)session.getValue("user");
%>

<head>
  <title>Memopad</title>
</head>

<%-- INCLUDE THE HEADER --%>
<jsp:include page="global/header.jsp" flush="true" />

<blockquote>

  <form method="POST" action="memoController.jsp">
    <div align="left">
    <table border="0" cellpadding="5" cellspacing="1">
      <%-- print error messages if they exist --%>
      <%
      String errorMsg = (String)session.getValue("errorMsg");
      %>
      <tr>
        <td colspan=3 align=left valign=top>
          <% if ( errorMsg != null ) { %>
            <font color=red><b><%=errorMsg%></b></font>
          <% } %>
        </td>
      </tr>
      <% session.removeValue("errorMsg"); %>

      <tr>
        <td valign="top" align="center" bgcolor="#C0C0C0"><font color="#000080"><strong>Memo Description</strong></font></td>
        <td valign="top" align="center" bgcolor="#C0C0C0"><font color="#000080"><strong>Select</strong></font></td>
        <td>&nbsp;</td>
      </tr>

        <%
          //----------------------------------------
          // List the available memos for this user.
          //----------------------------------------
          Memo memo = new Memo();
          Vector mlist = user.memos.getMemos(user.getUserid(),conn);
          Enumeration e = mlist.elements();
          while ( e.hasMoreElements() )
          {
            memo = (Memo)e.nextElement();
            %>
              <%-- print one full row per task --%>
              <tr>
                <td valign="top" align="left" bgcolor="#DDDDDD"><%=memo.getDescription()%></td>
                <td valign="top" align="left" bgcolor="#DDDDDD">
                <input type="radio" value="<%=memo.getID()%>" name="MEMO_ID"></td>
                <td>&nbsp;</td>
              </tr>
            <%
          }
          %>
    </table>
    </div>
    <p><input
            type="submit"
            value="  Add  "
            name="ADD"><input
            type="submit"
            value="  Edit  "
            name="EDIT"><input
            type="submit"
            value="  Delete  "
            name="DELETE"><input
            type="submit"
            value="  Print View  "
            name="PRINT_VIEW"></p>
  </form>
</blockquote>

</body>
</html>
