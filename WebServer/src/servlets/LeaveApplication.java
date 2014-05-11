package servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LeaveApplication
 */
@WebServlet("/LeaveApplication")
public class LeaveApplication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveApplication() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String leave = request.getParameter("user");
		System.out.println("Bye " + leave);
		
		UsersSingleton users = UsersSingleton.getInstance();
		ArrayList <User> userList = new ArrayList<User>(users.getUsr());
		
		System.out.println("Bye " + userList);
		
		for (User usr : userList){
			if (usr.getName().equals(leave)){
				users.getUsr().remove(users.getUsr().indexOf(usr));
			}
		}
		
		System.out.println("ByeBye " + users.getUsr());
		ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
		oos.writeObject("morti");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
