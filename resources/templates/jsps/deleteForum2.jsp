<%@ page import="java.sql.*,com.devdaily.dg.Forum,com.devdaily.dg.ForumList"

%>
<%
    Connection conn = (Connection) session.getValue("connection");
    if (conn == null)
    {
        out.println("Lost database connection.  Please re-login and try again.");
        return;
    }

    String forum_id = request.getParameter("FORUM");
    int id = 0;
    if ( forum_id == null )
    {
			  session.putValue("ERROR_MSG", "No forum was selected.");
			  %>
			  <jsp:forward page="listForums.jsp?f=delete" />
			  <%
    }

    try
    {
        id = Integer.parseInt(forum_id);
    }
    catch(NumberFormatException e)
    {
        out.println("Unexpected Error: forum id is not an integer");
        return;
    }

    Forum f = ForumList.getForumObjectFromID(id, conn);
    if (f == null)
    {
        out.println("Forum is not found.  Please try again.");
        return;
    }
    else
    {
        session.putValue("DELETE_FORUM", f);
    }
    String description = (f.getDescription() == null) ? "" : f.getDescription();
%>

<html>

<head>
<title></title>
</head>

<body bgcolor="#FFFFFF">
<h2>Delete a Forum - Confirmation</h2>

<br>
    <div align="left">
      <table border="0" cellpadding="4" cellspacing="1">
        <tr>
          <td valign="top" align="left"><b>Step 2:</b></td>
          <td valign="top" align="left">You are about to delete the Forum shown below and all of it's Topics
        and Messages. <br><font color=red>Are you sure???</font></td>
        </tr>
      </table>
    </div>

<br>
<form method="POST" action="deleteForum3.jsp">
  <blockquote>
    <div align="left">
      <table border="0" cellpadding="4" cellspacing="1">
        <tr>
          <td valign="top" align="left" bgcolor="#000080"><font color="#ffffff"><b>Forum Name:</b></font></td>
          <td valign="top" align="left" bgcolor="#C0C0C0"><%=f.getName()%></font></td>
        </tr>
        <tr>
          <td valign="top" align="left" bgcolor="#000080"><font color="#ffffff"><b>Description:</b></font></td>
          <td valign="top" align="left" bgcolor="#C0C0C0"><%=description%></td>
        </tr>
        <tr>
          <td valign="top" align="left" bgcolor="#000080"><font color="#ffffff"><b>Moderator:</b></font></td>
          <td valign="top" align="left" bgcolor="#C0C0C0"><%=f.getModerator()%></td>
        </tr>
        <tr>
          <td valign="top" align="left" bgcolor="#000080"><font color="#ffffff"><b>Sequence Number:</b></font></td>
          <td valign="top" align="left" bgcolor="#C0C0C0"><%=f.getSequenceNumber()%></td>
        </tr>
        <tr>
          <td valign="top" align="left" bgcolor="#000080">&nbsp;</td>
          <td bgcolor="#C0C0C0" align="left"><br><input
              type="submit"
              value="  Confirm Deletion  "
              name="CONFIRM_DELETE"><input
             type="button"
             value=" Cancel  "
             onClick="window.location.href='../help.html'">
          </td>
        </tr>
      </table>
    </div>
    <p>&nbsp;</p>
  </blockquote>
  <p>&nbsp;</p>
</form>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>

</html>
