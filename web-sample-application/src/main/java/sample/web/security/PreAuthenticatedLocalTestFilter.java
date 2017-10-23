package sample.web.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.MappableAttributesRetriever;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component("preAuthenticatedLocalTestFilter")
@Profile("!test")
public class PreAuthenticatedLocalTestFilter extends OncePerRequestFilter{
	@Autowired MappableAttributesRetriever mappableRolesRetriever;
	
	private String defaultRolePrefix = "ROLE_";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		SecurityContext securityContext = (SecurityContext)session.getAttribute("security_context");
		
		if(
				securityContext != null 
		) {
			filterChain.doFilter(request, response);
			return;
		}
		
		
			String username = request.getParameter("username");
			if(username == null) {
				
				
			
				Set<String> rolenames = mappableRolesRetriever.getMappableAttributes();
				
				PrintWriter out = response.getWriter();
				out.println("<html>"
						+ "		<body>");
						out.println("<h1>Test Login</h1>");
						out.println("<form action=\"testlogin\" method=\"GET\">");
						out.println("<input type=\"text\" name=\"username\" length=\"20\"/>");
						out.println("<select name=\"role\">");
						for(String role:rolenames) {
							out.println("<option>"+role+"</option>");	
						}
						out.println("</select>");
						out.println("<input type=\"submit\" value=\"submit\" />");
						out.println("</form>");
				
				out.println(	"</body>"
						+ "	</html>");
				
				return;
			}else {
				System.out.println("username is" + username);
				List<String> roles = Arrays.asList(request.getParameter("role"));
				List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority> ();
				for(String role:roles) {
					grantedAuthorities.add(new SimpleGrantedAuthority(defaultRolePrefix == null ?role:defaultRolePrefix+role));
				}
				Authentication authentication = new PreAuthenticatedAuthenticationToken(username, null, grantedAuthorities);
				securityContext = new SecurityContextImpl();
				securityContext.setAuthentication(authentication);
				SecurityContextHolder.setContext(securityContext);
				
				session.setAttribute("security_context", securityContext);
				
				response.sendRedirect(request.getContextPath());
			}
				
		
	}

	public void setDefaultRolePrefix(String defaultRolePrefix) {
		this.defaultRolePrefix = defaultRolePrefix;
	}
	
	
}
