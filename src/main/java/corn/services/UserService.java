package corn.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Álvaro Rungue
 * @since 04/01/15.
 */
@Service
public class UserService {

	@Transactional
	public HttpSession setUserSession(){

		//Recuperar os dados da authenticação do usuario
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//Recuperar a sessão do usuario
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = servletRequestAttributes.getRequest().getSession();

//		//Busvar no banco o usuario atualmente conectado nesse contexto
//		User currentUser = this.findByUniqueAttribute("login", auth.getName());
//
//		//Adiciona Informaçoes do usuário na sessão
//		if(auth.isAuthenticated()){
//			session.setAttribute("id",         currentUser.getId());
//			session.setAttribute("name",       currentUser.getName());
//			session.setAttribute("email",      currentUser.getEmail());
//			session.setAttribute("logged",     true);
//			session.setAttribute("instance",   currentUser.getInstance() );
//			session.setAttribute("last_login", currentUser.getLastLogin());
//
//			//Atualizar o ultimo login na sessao
//			currentUser.setLastLogin(new Date());
//			this.userDAO.update(currentUser);
//		}//fim if()
//
		return session;
	}//fim setUserSession()


}//fim class UserService
