<%@ page import="java.util.*" errorPage="error.jsp" %>

<%-- GET THE SESSION FROM THE SESSION HEADER --%>
<%@ include file="global/sessionHeader.jsp" %>

<html>

<%
   //Connection conn   = (Connection)session.getValue("connection");
   //**CLASS_NAME** current**CLASS_NAME** = **CLASS_NAME**DO.get**CLASS_NAME**ObjectFromID(memoId, user.getUserid(), conn);
%>

<head>
  <title>**CLASS_NAME** Print View</title>
</head>


<%-- START BODY --%>
<body bgcolor="#ffffff">

<%-- INCLUDE THE HEADER --%>
<jsp:include page="global/header.jsp" flush="true" />

<br>&nbsp;<br>

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
            type="button"
            value="  Back  "
            onClick="window.location.href='GO_SOMEWHERE.jsp'">
        </td>
      </tr>
    </table>
    </td></tr>
    </table>
    </div>

<p>&nbsp;</p>
</body>
</html>
