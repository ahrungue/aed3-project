package corn.interceptors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author Álvaro Rungue
 * @since 14/01/15.
 */
public class CustomInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object logged = request.getSession().getAttribute("logged");

		//vericar se existe o atributo logged na sessão
		if( logged == null ){
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String user = authentication.getName();

			if( !user.equals("anonymousUser")  ){
				//Recuperar a sessão do usuario
				ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
				HttpSession session = servletRequestAttributes.getRequest().getSession();
				GrantedAuthority grantedAuthority = authentication.getAuthorities().stream().findAny().orElse(null);
				session.setAttribute("logged",     true);
				session.setAttribute("last_login", new Date() );
				session.setAttribute("role", grantedAuthority.getAuthority() );
			}//fim if()
		}//fim if(logged)

		return super.preHandle(request, response, handler);
	}//fim preHandle()

}//fim class CustomInterceptor
