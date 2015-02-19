package corn.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Álvaro
 * @since 05/01/15.
 */
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		GrantedAuthority grantedAuthority = authentication.getAuthorities().stream().findAny().orElse(null);

		if( grantedAuthority.getAuthority().equals("ROLE_ADMIN") ){
			setDefaultTargetUrl("/#/question");
		}else{
			setDefaultTargetUrl("/#/play/start");
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}//fim onAuthenticationSuccess()

}//fim class CustomAuthenticationSuccessHandler
