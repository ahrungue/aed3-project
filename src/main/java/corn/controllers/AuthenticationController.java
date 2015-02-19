package corn.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @author alvaro
 * @since 26/12/14.
 */
@Controller
public class AuthenticationController {

	@RequestMapping( value ="/login", method = RequestMethod.GET)
	public String auth(HttpServletResponse response){
		response.setStatus(401);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if(!authentication.getName().equals("anonymousUser")){
			return "redirect:/";
		}//fim if(anonymousUser)

		return "/WEB-INF/views/auth.jsp";
	}//fim auth()

}//fim class AuthenticationController
