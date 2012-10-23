package core;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.appserv.jdbc.DataSource;
import com.sun.corba.se.impl.util.Version;

/**
 * Servlet implementation class addReviewServlet
 */
@WebServlet("/addReviewServlet")
public class addReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addReviewServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/add_review.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			String school_id = request.getParameter("school_id");
			String name = request.getParameter("name");
			String review = request.getParameter("review");
			
			InitialContext context = new InitialContext();
			DataSource source = (DataSource) context.lookup("jdbc/lut2");
			Connection connection = source.getConnection();

			PreparedStatement SQLstatement = connection.prepareStatement("INSERT INTO user_reviews VALUES(?, ?, ?)");
			
			SQLstatement.setString(1, school_id);
			SQLstatement.setString(2, encodeHTML(name));
			SQLstatement.setString(3, encodeHTML(review));
			
			SQLstatement.executeUpdate();

		} catch (SQLException ex) {
		} catch (NamingException e) {
		}

		request.getRequestDispatcher("/add_review.jsp").forward(request,
				response);
	}

	public static String encodeHTML(String input) {
		String out = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			String s = escape(c);
			out += s;
		}

		return out;
	}
	
	private static String escape(Character c) {
		switch (c) {
		case ((char) 60): // <
			return "&lt";
		case ((char) 62): // >
			return "&gt";
		case ((char) 47): // /
			return "&#x2F";
		case ((char) 92): // \
			return "&#x5C";
		case ((char) 39): // '
			return "&#x27";
		case ((char) 34): // "
			return "&quot";
		case ((char) 38): // &
			return "&amp";

		default:
			return c.toString();
		}
	}

}
