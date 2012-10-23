import java.sql.*;
import javax.naming.InitialContext;
import com.sun.appserv.jdbc.DataSource;

public class UserConnection {

	static Connection connection = null;
	static ResultSet resultSet = null;

	public static UserSession login(UserSession session) {
		PreparedStatement pst = null;
		String username = session.getUsername();
		String password = session.getPassword();

		try {
			// connect
			connection = createConnection();
			connection.setAutoCommit(false);
			// execute
			String sqlQuery = "SELECT * FROM uname WHERE username=? AND pw=?";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			resultSet = pst.executeQuery();
			connection.commit();
			// check result
			if(resultSet.next()){
				session.setLoggedIn(true);
			} else {
				session.setLoggedIn(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				pst.close();
				connection.close();
			} catch (Exception e) {				
			}
		}
		return session;
	}

	private static Connection createConnection() {
		Connection c = null;
		try {
			DataSource ds = (DataSource) new InitialContext().lookup("jdbc/lut2");
			c = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
}
