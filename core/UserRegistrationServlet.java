package core;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserRegistrationServlet
 */
@WebServlet("/UserRegistrationServlet")
@ServletSecurity(httpMethodConstraints = {
	    @HttpMethodConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL,
	            value = "POST"),
	    @HttpMethodConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL,
	    value = "GET")
	})
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegistrationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{	    
		     UserSession user = new UserSession();
		     user.setUsername(request.getParameter("username_j"));
		     user.setPassword(request.getParameter("password_j"));
		     user.setQuestion(request.getParameter("question"));
		     user.setAnswer(request.getParameter("answer"));
		     user = UserConnection.create(user);
//		     System.out.println(request.getParameter("username_j"));
//		     System.out.println(request.getParameter("password_j"));
//		     System.out.println(user.isLoggedIn());
		     
		     if (user.isLoggedIn()){
		    	 System.out.println();
		          HttpSession session = request.getSession(true);
		          session.setMaxInactiveInterval(60*60); // one hour
		          session.setAttribute("currentUser",user); 
		          response.sendRedirect("index.jsp");    		
		     }       
		     else{
		    	 
		    	 request.getRequestDispatcher("userLogin.jsp").forward(request, response);
		     }
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
