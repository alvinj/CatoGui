<%@ page import="java.sql.*,java.util.*,com.devdaily.web.PagedList" errorPage="error.jsp" %>

<%
   Connection conn      = (Connection)session.getValue("CONNECTION");
   String     errorMsg  = (String)session.getValue("ERROR_MSG");

   String username = (String)session.getValue("USERNAME");
   String currentForumIDString = (String)session.getValue("CURRENT_FORUM_ID");
   String currentPageString    = (String)session.getValue("CURRENT_PAGE");
   int currentForumIDInt;
   int currentPageInt;
   try
   {
     currentForumIDInt = (new Integer(currentForumIDString)).intValue();
     currentPageInt    = (new Integer(currentPageString)).intValue();
   }
   catch (NumberFormatException nfe)
   {
     currentForumIDInt = 1;
     currentPageInt    = 1;
   }

   Vector listOfForums = (Vector)session.getValue("LIST_OF_FORUMS");
%>

<html>

<head>
<title>**CLASS_NAME** List</title>

<SCRIPT LANGUAGE="JavaScript">
  function siteJump2(siteSelector)
  {
    self.location = siteSelector.options[siteSelector.selectedIndex].value;
  }
</SCRIPT>

</head>

<body bgcolor="#ffffff">

    <%-- HERE IS THE MAIN "TOPICS" TABLE --%>
    <div align="left"><table border="0" cellpadding="5" cellspacing="1" width="100%">
      <%-- START ROW THAT SHOWS THE CURRENT FORUM --%>
      <tr>
        <%
        String currentForumName = Forum.getForumName(listOfForums,currentForumIDInt);
        session.putValue("CURRENT_FORUM_NAME",currentForumName);
        %>
        <td colspan=5 align="left" valign="top" bgcolor="white">
          <%=TH_FONT_TAG%><br><b>Current forum:</b>&nbsp;<em><%=currentForumName%></em</font>
        </td>
      </tr>
      <%-- END ROW THAT SHOWS THE CURRENT FORUM --%>

      <tr>
        <th align="center" bgcolor="<%=TH_BGCOLOR%>" valign="top"></th>
        **FOREACH_FIELD**
        <th align="center" bgcolor="<%=TH_BGCOLOR%>"><%=TH_FONT_TAG%>**FIELD_LABEL**</font></th>
        **END_FOREACH_FIELD**
      </tr>

      <%-- print one row for each topic --%>
      <%

      Hashtable numMessagesPerTopic  = **DATA_OBJECT_NAME**.getNumMessagesByTopic(conn);
      Hashtable lastPostDatePerTopic = **DATA_OBJECT_NAME**.getLastMessagePostDateByTopic(conn);

      // List the topics for this forum...
      Topic t = null;
      TopicList topicList = new TopicList();

      // AJA: TODO: FIX THIS:
      int itemsPerPage = 10;

      PagedList pagedListOfTopics = topicList.getPagedList(conn,currentForumIDInt,currentPageInt,itemsPerPage);
      Vector listOfTopics = pagedListOfTopics.getList();
      Enumeration e = listOfTopics.elements();
      while ( e.hasMoreElements() )
      {
        t = (Topic)e.nextElement();
        int numMessages = ((Integer)numMessagesPerTopic.get( new Integer(t.getTopicID()) )).intValue();
        String lastPostDate = (String)lastPostDatePerTopic.get( new Integer(t.getTopicID()) );
        // must account for the possibility of topics that have no subject.
        // do not display these.
        if ( !t.getSubject().trim().equals("") )
        {
          %>
          <tr>
            <td align="center" bgcolor="<%=TD_BGCOLOR_LIGHT%>" valign="top"><img src="folder.gif" alt="folder.gif (935 bytes)" WIDTH="20" HEIGHT="20"></td>
            <td align="left" bgcolor="<%=TD_BGCOLOR_DARK%>"><a
              href="showTopicCtrl.jsp?TOPIC_ID=<%=t.getTopicID()%>"><%=TD_FONT_TAG%><strong><%=t.getSubject()%></strong></font></a><br>
            </td>
            <td align="center" bgcolor="<%=TD_BGCOLOR_LIGHT%>"><%=TD_FONT_TAG%><%=t.getTopicStarter()%></font></td>
            <td align="center" bgcolor="<%=TD_BGCOLOR_DARK%>"><%=TD_FONT_TAG%><%=numMessages%></font></td>
            <td align="center" bgcolor="<%=TD_BGCOLOR_LIGHT%>"><%=TD_FONT_TAG%><%=lastPostDate%></font></td>
          </tr>
          <%
        }
      }
      %>

      <%-- create PrevPage/NextPage buttons only if needed --%>
      <% if ( pagedListOfTopics.getCurrentPage() > 1  ||  pagedListOfTopics.hasNextPage() )   { %>
      <tr>
        <td colspan=5 align="center" bgcolor="white" valign="top">
          <form method="POST" action="forumsMainCtrl.jsp">
            <input type="hidden" name="FORUM_ID" value="<%=currentForumIDInt%>">
            <% if ( pagedListOfTopics.getCurrentPage() > 1 )   { %>
                <input type="hidden" name="PREVIOUS_PAGE" value="<%=currentPageInt-1%>">
                <input type="submit" value="  << Previous  " name="PREVIOUS">
            <% } %>
            &nbsp;&nbsp;
            <% if ( pagedListOfTopics.hasNextPage() )   { %>
                <input type="hidden" name="NEXT_PAGE" value="<%=currentPageInt+1%>">
                <input type="submit" value="  Next >>  " name="NEXT">
            <% } %>
          </form>
        </td>
      </tr>
      <% } %>

    </table>
    </div>

      <%-- "JUMP" FORM STARTS --%>
      <div align="center"><center><table border="0" cellpadding="5" cellspacing="1" width="100%">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <FORM NAME="JUMPFORM" method="get">
          <td width="100%" align="right"><%=TD_FONT_TAG%>Jump To: </font><select name="TOPIC" size="1">
            <%
            Enumeration e2 = listOfForums.elements();
            while ( e2.hasMoreElements() )
            {
              Forum f = (Forum)e2.nextElement();
              %>
              <option value="http://localhost:8080/examples/discGroup/forumsMainCtrl.jsp?FORUM_ID=<%=f.getID() %>"><%=f.getName() %></option>
              <%
            }
            %>
            </select>
            <input type=button value=" Go " onClick="siteJump2(document.JUMPFORM.TOPIC)">
          </td>
          </FORM>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table>
      </center>
     </div>
     <%-- "JUMP" FORM ENDS --%>

    <div align="center"><center><table border="0" cellpadding="5" cellspacing="1" width="90%">
      <tr>
        <td width="100%" align="center"><a
            href="forumsMain.jsp"><%=TD_FONT_TAG%>mondo home</font></a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
            href="postNewTopic.jsp"><%=TD_FONT_TAG%>post new topic</font></a>
        </td>
      </tr>
      <tr>
        <td width="100%" align="center"></td>
      </tr>
    </table>
    </center></div></td>
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
