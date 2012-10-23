

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserLoginServlet
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{	    
		     UserSession user = new UserSession();
		     user.setUsername(request.getParameter("username"));
		     user.setPassword(request.getParameter("password"));		
		     user = UserConnection.login(user);

		     if (user.isLoggedIn()){
		          HttpSession session = request.getSession(true);
		          session.setMaxInactiveInterval(60*60); // one hour
		          session.setAttribute("currentSessionUser",user); 
		          response.sendRedirect("index.jsp");    		
		     }       
		     else{
		    	 request.setAttribute("errorMessage", "Wrong username or password");
		    	 //request.getRequestDispatcher("userLogin.jsp").forward(request, response);
		     }
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
