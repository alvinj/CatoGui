package dbgeneric;

import org.ewin.sql.*;

/**
  * A final class for storing the connection pool
  * Otherwise those that extend the DatabaseAccess class
  * instantiate their own pool.
  *
  * @author J.A.Carter
  * @version 1.0
  */
public
class DbGlobal
{
/**
  * This use of a static ConnectionManager allows all the
  * extended classes to use the same pool of connections to the
  * database. This is very effecient. The only downside is that
  * it effectively prevents connections to multiple databases.
  * To achieve multiple databases, this class would have to be copied
  * to a <b>different</b> name and hence give multiple connection pools
  */
  public static ConnectionManagerMin cm;

}
