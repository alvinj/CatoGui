<%
  String currentAppName = (String)session.getValue("currentAppName");
%>
<body bgcolor="white" link="#000080" vlink="#000080" alink="#000080">

<div align="left">
<table border="0" cellspacing="0" width="100%" bgcolor="#000080">
  <tr>
    <td width="100%">

      <table border="0" cellpadding="2" cellspacing="0" width="100%">

      <tr>
        <td valign="top" align="left" bgcolor="#000080"><font color="#FFFFFF">&nbsp;my.devdaily.com</font></td>
        <td valign="top" align="right" bgcolor="#000080"><a href="logout.jsp"><font color="#FFFFFF">logout</font></a>&nbsp;</td>
      </tr>

      <tr>
        <td valign="top" align="left" bgcolor="silver"><font
            color="#000080">&nbsp;<em><b><%=currentAppName%></b></em></font>
        </td>
        <td valign="bottom" align="right" bgcolor="silver"><font color="#000080"><a
        href="bookmarksMain.jsp">bookmarks</a>&nbsp;|&nbsp;<a
        href="calendar.jsp">calendar</a>&nbsp;|&nbsp;<a
        href="contactsMain.jsp">contacts</a>&nbsp;|&nbsp;<!-- <a
        href="notAvailable.jsp">email</a>&nbsp;|&nbsp; --><a
        href="memoMain.jsp">memos</a>&nbsp;|&nbsp;<a
        href="toDoMain.jsp">to-do&nbsp;list</a>&nbsp;|&nbsp;<a
        href="http://www.devdaily.com" target="_blank">devdaily</a>&nbsp;</font></td>
      </tr>

    </table>

    </td>
  </tr>
</table>
</div>
