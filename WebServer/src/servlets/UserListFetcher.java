package servlets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;



@WebServlet("/UserListFetcher")
public class UserListFetcher extends HttpServlet {

	private static final long serialVersionUID = 1L;
    HashMap<String, Integer> users = new HashMap<String, Integer> ();
    
    
    UsersList usr; 
    		
    public UserListFetcher() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	
		ArrayList<User> u = new ArrayList<>();
		UsersSingleton singleton = UsersSingleton.getInstance();
		for (User us : singleton.getUsr()){
			u.add(us);
		}
		
		usr = new UsersList(u);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(usr);
		
	    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
	    oos.writeObject(jsonString);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
