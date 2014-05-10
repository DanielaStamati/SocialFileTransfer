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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;


@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {

	private static final long serialVersionUID = 1L;
    HashMap<String, Integer> users = new HashMap<String, Integer> ();
    
    
    UsersList usr; 
    		
    public RegisterUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		ArrayList<String> files = new ArrayList<String> ();
		files.add ("dfile1");
		files.add("pfile1");
		User user = new User("animal", "127.0.0.1", 30000, files);
	
		ArrayList<User> u = new ArrayList<>();
		u.add(user);
		
		usr = new UsersList(u);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(usr);
		System.out.println(jsonString);
	    ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
	    oos.writeObject(jsonString);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
