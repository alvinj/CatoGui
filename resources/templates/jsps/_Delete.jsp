<%@ page import="java.util.*,com.devdaily.Utils,**PACKAGE_NAME**.**CLASS_NAME**" %>

<%
   Connection conn = (Connection)session.getValue("CONNECTION");
   **CLASS_NAME** current**CLASS_NAME** = (**CLASS_NAME**)session.getValue("CURRENT_**CLASS_NAME_UPPER**");
%>

<html>

<head>
<title></title>
</head>

<body bgcolor="#FFFFFF">

<h2><font color="#000080">Delete a **CLASS_NAME**</font></h2>

<p><b>Step 1:</b> Choose a **CLASS_NAME** to delete.</p>

<form method="POST" action="**CLASS_NAME**Controller">

  <input type="hidden" name="ACTION" value="DELETE">
  <input type="hidden" name="CURRENT_PAGE" value="**CLASS_INSTANCE_NAME**Delete.jsp">
  <blockquote>
    <div align="left">
      <table border="0" cellpadding="4" cellspacing="1">

        <tr>
          <td valign="top" align="center" bgcolor="#000080">&nbsp;</td>
          **FOREACH_FIELD**
          <td valign="top" align="center" bgcolor="#000080"><b><font color="#FFFFFF" size="4">**FIELD_LABEL**</font></b></td>
          **END_FOREACH_FIELD**
        </tr>

        // start listing forums here ...

        <%
          // Get the number of topics per forum
          Hashtable numTopicsPerForum    = ForumList.getNumTopicsByForum(conn);
          Hashtable lastPostDatePerForum = ForumList.getLastTopicPostDateByForum(conn);

          // List the active forums.
          **CLASS_NAME** **CLASS_INSTANCE_NAME** = null;
          List **CLASS_INSTANCE_NAME**List = new **DATA_OBJECT_NAME**.selectAll(conn);

          //session.putValue("LIST_OF_FORUMS", listOfForums); // put this to the session so we can retrieve it easily
          Iterator it = **CLASS_INSTANCE_NAME**List.iterator();
          while ( it.hasNext() )
          {
            **CLASS_INSTANCE_NAME** = (**CLASS_NAME**)it.next();

            <%-- FIX -- FIX -- FIX     NEEDS A LOT OF WORK HERE --%>

            int numTopics = ((Integer)numTopicsPerForum.get( new Integer(f.getID()) )).intValue();
            String lastPostDate = (String)lastPostDatePerForum.get(  new Integer(**CLASS_INSTANCE_NAME**.get**FIELD_NAME**())  );
            %>
            <%-- print one full formatted row per forum --%>
            <tr>
              <td valign="top" align="left" bgcolor="#DFDFDF"><input type="radio" value="F3" checked name="FORUM"></td>

**FOREACH_FIELD**
              <td align="left" bgcolor="#C0C0C0"><u><%=TD_FONT_TAG%><strong><%=**CLASS_INSTANCE_NAME**.get**FIELD_NAME**()%></strong></font></u><br>
**END_FOREACH_FIELD**
            </tr>
            <%
          }
          %>


        <tr>
          <td valign="top" align="left" bgcolor="#DFDFDF"><input type="radio" value="F3" checked name="FORUM"></td>
          <td valign="top" align="left" bgcolor="#DFDFDF">NAME HERE</td>
          <td valign="top" align="left" bgcolor="#DFDFDF">DESCRIPTION</td>
        </tr>

        <tr>
          <td bgcolor="#DFDFDF" colspan="3" align="center"><input type="submit" value="  Delete Selected Item  " name="DELETE"></td>
        </tr>
      </table>
    </div>
    <p>&nbsp;</p>
  </blockquote>
  <p>&nbsp;</p>
</form>

<p>&nbsp;</p>
</body>

</html>
