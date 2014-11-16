<%@ page import="**PACKAGE_NAME**.**CLASS_NAME**" errorPage="error.jsp" %>

<html>
<head>
  <title>Add a new **CLASS_NAME**</title>
</head>

<body bgcolor=white>

<h2><font color="#000080">Add a new **CLASS_NAME**</font></h2>

<blockquote>

<form action="**CLASS_NAME**Controller" method="post">

  <input type="hidden" name="ACTION" value="ADD">
  <input type="hidden" name="CURRENT_PAGE" value="**CLASS_INSTANCE_NAME**Add.jsp">

  <table cellpadding=4 cellspacing=1>

  **FOREACH_FIELD**
    <tr>
      <td align=left valign=top>
        <b>**FIELD_LABEL**:</b>
      </td>
      <td align=left valign=top>
        <input type="text" name="**FIELD_NAME**">
      </td>
    </tr>
  **END_FOREACH_FIELD**


    <tr>
      <td align=left valign=top>
        &nbsp;
      </td>
      <td align=left valign=top>    <input type=submit name="ADD" value="  Add  "><input
             type="button"
             value="  Cancel  "
             onClick="window.location.href='GO_SOMEWHERE.jsp'">
      </td>
    </tr>
  </table>

</form>
</blockquote>
</body>
</html>
