<%@ page import="" errorPage="error.jsp" %>

<%-- GET THE SESSION FROM THE SESSION HEADER --%>
<!-- <%@ include file="global/sessionHeader.jsp" %> -->

<html>

<%
   Connection conn   = (Connection)session.getValue("connection");
   //User       user   = (User)session.getValue("user");
   //int        memoId = ((Integer)session.getValue("memoId")).intValue();

   **CLASS_NAME** current**CLASS_NAME** = (**CLASS_NAME**)session.getValue("CURRENT_**CLASS_NAME_UPPER**");
%>

<head>
  <title>Confirm **CLASS_NAME** Deletion</title>
</head>


<%-- START BODY --%>
<body bgcolor="#ffffff">

<%-- INCLUDE THE HEADER --%>
<jsp:include page="global/header.jsp" flush="true" />


<h2><font color="#000080">Delete this **CLASS_NAME**?</font></h2>

<blockquote>
  <form method="POST" action="**CLASS_NAME**Controller">
  <input type="hidden" name="ACTION" value="COMMIT_DELETE">
  <input type="hidden" name="CURRENT_PAGE" value="**CLASS_INSTANCE_NAME**ConfirmDelete.jsp">

    <div align="left">
    <table bgcolor="#000080" border="0" cellpadding="1" cellspacing="0">
    <tr><td>
    <table border="0" cellpadding="5" cellspacing="1" width="100%">

      **FOREACH_FIELD**
      <tr>
        <td bgcolor="#f0f0f0" align=left valign="top"><b>**FIELD_LABEL**:</b></td>
        <td bgcolor="#ffffff" align=left valign="top"><%=current**CLASS_NAME**.get**FIELD_LABEL**()%></td>
      </tr>
      **END_FOREACH_FIELD**

      <tr>
        <td bgcolor="#f0f0f0">&nbsp;</td>
        <td bgcolor="#ffffff" align=left valign="top">
          <br>
          <input
            type="submit"
            value="  Delete  "
            name="DELETE"
          ><input
            type="button"
            value="  Cancel  "
            onClick="window.location.href='DO_SOMETHING.jsp'">
        </td>
      </tr>
    </table>
    </td></tr>
    </table>
    </div>
  </form>
</blockquote>

<p>&nbsp;</p>
</body>
</html>
