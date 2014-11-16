<%@ page import="java.sql.*,**PACKAGE_NAME**.**CLASS_NAME**,**PACKAGE_NAME**.**DATA_OBJECT_NAME**" errorPage="error.jsp" %>

<!-- DEVELOPER: THIS MAY NOT BE VALID FOR YOUR APP -->
<!-- <%@ include file="global/sessionHeader.jsp" %> -->

<html>

<%
   Connection conn   = (Connection)session.getValue("connection");
   **CLASS_NAME** current**CLASS_NAME** = (**CLASS_NAME**)session.getValue("CURRENT_**CLASS_NAME_UPPER**");
   //User       user   = (User)session.getValue("USER");

%>

<head>
  <title>Edit **CLASS_NAME**</title>
</head>


<h2><font color="#000080">Edit **CLASS_NAME**</font></h2>

<blockquote>

  <form method="POST" action="**CLASS_NAME**Controller">

    <input type="hidden" name="ACTION" value="COMMIT_EDIT">
    <input type="hidden" name="CURRENT_PAGE" value="**CLASS_INSTANCE_NAME**Edit.jsp">

    <div align="left">
    <table border="0" cellpadding="5" cellspacing="0">

      **FOREACH_FIELD**
      <tr>
        <td align="left" valign="top"><b>**FIELD_LABEL**:</b></td>
        <td align="left" valign="top"><input type="text" name="**FIELD_NAME**" value="<%=current**CLASS_NAME**.get**FIELD_LABEL**()%>">
        </td>
      </tr>
      **END_FOREACH_FIELD**

      <tr>
        <td align="left" valign="top"></td>
        <td align="left" valign="top">
          <br>
          <input
            type="submit"
            value="  Save Changes  "
            name="SAVE_CHANGE"
          ><input
            type="button"
            value="  Cancel  "
            onClick="window.location.href='GO_SOMEWHERE.jsp'">
        </td>
      </tr>
    </table>
    </div>
  </form>
</blockquote>

<p>&nbsp;</p>
</body>
</html>
