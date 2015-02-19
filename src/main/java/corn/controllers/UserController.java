package corn.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Álvaro Rungue
 * @since 26/12/14.
 */
@Controller
public class UserController {

	@RequestMapping( value ="/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		//caso usuario não esteja autenticado redirecionar para o login
		if(!authentication.isAuthenticated()){
			return "redirect:/login";
		}//fim if(authentication)

		//Caso usuario esteja authenticado redirecionar para o index
		return "/app/index.html";
	}//fim home()

}//fim class UserController