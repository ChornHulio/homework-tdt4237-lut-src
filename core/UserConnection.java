package core;
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
			// execute
			String sqlQuery = "SELECT * FROM users WHERE uname=? AND pw=?";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			resultSet = pst.executeQuery();
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

	public static UserSession getQuestion(UserSession session) {
		PreparedStatement pst = null;
		String username = session.getUsername();

		try {
			// connect
			connection = createConnection();
			// execute
			String sqlQuery = "SELECT * FROM users WHERE uname=?";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			resultSet = pst.executeQuery();
			String question;
			// check result
			if(resultSet.next()){
				question = resultSet.getString("question");
			} else {
				question = "";
			}
			session.setQuestion(question);
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

	public static UserSession create(UserSession session) {
		PreparedStatement pst = null;
		String username = addReviewServlet.encodeHTML(session.getUsername());
		String password = session.getPassword();
		String question = addReviewServlet.encodeHTML(session.getQuestion());
		String answer = session.getAnswer();

		try {
			// connect
			connection = createConnection();
			// execute
			String sqlQuery = "INSERT INTO users (uname, pw, adminrole, question, answer) VALUES (?, ?, ?, ?, ?);";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setBoolean(3, false);
			pst.setString(4, question);
			pst.setString(5, answer);
			int result =  pst.executeUpdate();
			session.setLoggedIn(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e) {				
			}
			try {
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

	public static UserSession resetPassword(UserSession session) {
		PreparedStatement pst = null;
		String username = session.getUsername();
		String password = session.getPassword();
		String answer = session.getAnswer();

		try {
			// connect
			connection = createConnection();
			// execute
			String sqlQuery = "SELECT * FROM users WHERE uname=? AND answer=?";
			pst = connection.prepareStatement(sqlQuery);
			pst.setString(1, username);
			pst.setString(2, answer);
			resultSet = pst.executeQuery();
			// check result
			if(resultSet.next()){
				sqlQuery = "UPDATE users SET pw=? WHERE uname=? AND answer=?";
				pst = connection.prepareStatement(sqlQuery);
				pst.setString(1, password);
				pst.setString(2, username);
				pst.setString(3, answer);
				pst.executeUpdate();
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
}
