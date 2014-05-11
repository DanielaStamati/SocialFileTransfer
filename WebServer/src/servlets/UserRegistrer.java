package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet("/UserRegistrer")
public class UserRegistrer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static ArrayList<User> userList = new ArrayList<User>();
	String json;
	
    public UserRegistrer() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		json = request.getParameter("json");
		
		User user;
		
		ObjectMapper mapper = new ObjectMapper();
		user = mapper.readValue(json, User.class);
		
		System.out.println(user.files);
		
		UsersSingleton singleton = UsersSingleton.getInstance();
		singleton.getUsr().add(user);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
