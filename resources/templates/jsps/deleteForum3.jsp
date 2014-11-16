<%@ page import="java.sql.*,com.devdaily.dg.*" %>

<%
    Connection conn = (Connection) session.getValue("connection");
    if (conn == null)
    {
        out.println("Lost database connection.  Please re-login and try again.");
        session.invalidate();
        return;
    }
    Forum deleteForum = (Forum) session.getValue("DELETE_FORUM");
    if (deleteForum == null)
    {
        out.println("Lost Session.  Please re-login and try again.");
        session.invalidate();
        return;
    }

    session.removeValue("DELETE_FORUM");

    //MySql does not support transaction.  I tried to setAutoCommit(false) and do rollback
    // and commit, I got the Transaction not supported error.

    boolean status = MessageList.deleteMessagesByForumID(deleteForum.getID(), conn);
    //conn.setAutoCommit(false);
    if (!status)
    {
        //conn.rollback();
        out.println("Error occurred while try to delete messages related to this forum.");
        return;
    }
    else
    {
        status = TopicList.deleteTopicsByForumID(deleteForum.getID(), conn);
        if (!status)
        {
            //conn.rollback();
            out.println("Error occurred while try to delete topics related to this forum.");
            return;
        }
        else
        {
            status = ForumList.delete(deleteForum, conn);
            if (!status)
            {
                //conn.rollback();
                out.println("Error occurred while try to delete this forum.");
                return;
            }
	      /*
            else
            {
                //conn.commit();
            }
            */
        }
    }

%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title></title>
</head>

<body bgcolor="#FFFFFF">
<h2>Delete a Forum</h2>
<%
    if (status)
    {
%>
<p>The &quot;<%=deleteForum.getName()%>&quot; forum has been deleted.</p>
<%
    }
%>
</body>

</html>
