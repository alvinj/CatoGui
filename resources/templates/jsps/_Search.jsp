<%@ page import="java.util.*" errorPage="error.jsp" %>

<%
   Connection conn      = (Connection)session.getValue("CONNECTION");
   String     errorMsg  = (String)session.getValue("ERROR_MSG");
   String     username  = (String)session.getValue("USERNAME");

   List listOf**CLASS_NAME** = **DATA_OBJECT_NAME**.selectAll(connection);
%>

<html>

<head>
  <title>**CLASS_NAME** Search</title>

  <SCRIPT LANGUAGE="JavaScript">
    function siteJump2(siteSelector)
    {
      self.location = siteSelector.options[siteSelector.selectedIndex].value;
    }
  </SCRIPT>
</head>

<body>

<div align="center"><center>

<table border="0" cellpadding="5" cellspacing="1" width="90%">
  <tr>
    <td width="100%">

    <%-- SEARCH FORM BEGINS --%>
    <form action="**CLASS_NAME**SearchController" method="post">
      <div align="left">
      <table border="0" cellpadding="10" cellspacing="1" width="100%">
        <tr>
          <th colspan="2" align="center" valign="top" bgcolor="#ffffff">
            <%=TH_FONT_TAG%>**CLASS_NAME** Search</font>
          </th>
        </tr>

        <%
        if ( errorMsg != null )
        {
          %>
          <tr>
            <th colspan="2" align="center" valign="top" bgcolor="#ffffff">
              <%=TH_FONT_TAG%><font color=red><%=errorMsg%></font></font>
            </th>
          </tr>
          <%
          session.putValue("errorMsg", "");
        }
        %>

        <tr>
          <th align="right" valign="top" bgcolor="<%=TD_BGCOLOR_DARK%>"><%=TD_FONT_TAG%>Search for:</font></th>
          <td align="left" valign="top" checked="false" bgcolor="<%=TD_BGCOLOR_LIGHT%>">
            <input type="text" name="SEARCH_FOR" size="20"><br>
            <input type="radio" value="ALL" name="MATCH" checked="true"><%=TD_FONT_TAG%>Match <em>all</em> search terms above</font><br>
            <input type="radio" value="ANY" name="MATCH"><%=TD_FONT_TAG%>Match <em>any</em> search terms above</font>
          </td>
        </tr>
        <tr>
          <th align="right" valign="top" bgcolor="<%=TD_BGCOLOR_DARK%>"><%=TD_FONT_TAG%>Search forum:</font></th>
          <td align="left" valign="top" bgcolor="<%=TD_BGCOLOR_LIGHT%>">
            <%-- "FORUM" SELECT/OPTION STARTS --%>
            <select name="SEARCH_FORUM" size="1">
            <%
            Enumeration e = listOfForums.elements();
            while ( e.hasMoreElements() )
            {
              Forum f = (Forum)e.nextElement();
              if ( f.getID() == currentForumIDInt )
              {
                %> <option SELECTED value="<%=f.getID() %>"><%=f.getName() %></option>  <%
              }
              else
              {
                %> <option value="<%=f.getID() %>"><%=f.getName() %></option> <%
              }
            }// end while
            %>
            </select>
            <%-- "FORUM" SELECT/OPTION ENDS --%>
          </td>
        </tr>
        <!-- DISABLE DATE-BASED SEARCH FOR NOW
        <tr>
          <th align="right" valign="top" bgcolor="<%=TD_BGCOLOR_DARK%>"><%=TD_FONT_TAG%>Search by date:</font></th>
          <td align="left" valign="top" bgcolor="<%=TD_BGCOLOR_LIGHT%>">
            <select name="DATE" size="1">
              <option value="Any date">Any date</option>
              <option value="Last 45 days">Last 45 days</option>
              <option value="Last 30 days">Last 30 days</option>
              <option value="Last 15 days">Last 15 days</option>
            </select>
          </td>
        </tr>
        -->

        <tr>
          <th align="right" valign="top" bgcolor="<%=TD_BGCOLOR_DARK%>"></th>
          <td align="left" valign="top" checked="false" bgcolor="<%=TD_BGCOLOR_LIGHT%>"><input type="submit"
          value="  Search  " name="SEARCH"></td>
        </tr>
        <tr>
          <td align="center" valign="top" colspan="2">&nbsp;</td>
        </tr>

      </table>
      </div><div align="center"><center><table border="0" cellpadding="5" cellspacing="1"
      width="90%">
        <tr>
          <td width="100%" align="center"><a
          href="forumsMain.jsp"><%=TD_FONT_TAG%>mondo home</font></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
          href="http://www.devdaily.com"><%=TD_FONT_TAG%>devdaily home page</font></a></td>
        </tr>
        <tr>
          <td width="100%" align="center"></td>
        </tr>
      </table>
      </center></div>
    </form>
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
