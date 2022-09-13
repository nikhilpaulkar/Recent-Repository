package com.app.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.dto.UserDataDto;
import com.app.serviceImpl.AuthServiceImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private AuthServiceImpl authInterface;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		// this requestTokenHeader Contains the Berar token of the current user
		// -----vinay-----
		final String requestTokenHeader = request.getHeader("Authorization");
		// System.out.println("$#@$!@!@#$!@#$!@$#"+requestTokenHeader);
		MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest(request);
		String username = null;
		String jwtToken = null;
		JsonObject jsonObj = null;

		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// ----vinay----
		// only the Token
		if ((requestTokenHeader != null) && requestTokenHeader.startsWith("Bearer ")) {

			// this line display from 7 numbers means only token exclude the Bearer
			jwtToken = requestTokenHeader.substring(7);

			try {

				// username return data like a normal format text
				username = jwtTokenUtil.getEmailFromToken(jwtToken);
				// jsonObj return the data like json postman body
				jsonObj = JsonParser.parseString(username).getAsJsonObject(); // new
													// JsonParser().parse(username).getAsJsonObject();

			} catch (Exception e) {

				new Exception(e.getMessage());

			}

			// catch (ExpiredJwtException e) {
			// System.out.println("JWT Token has expired");
			// }
		} else {

			logger.warn("JWT Token does not begin with Bearer String");

		}

		// Once we get the token validate it.
		if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {

			UserDetails userDetails = this.authInterface.loadUserByUsername(jsonObj.get("email").getAsString());

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				UserDataDto userData = new UserDataDto();
				userData.setUserId((jsonObj.get("id").getAsLong()));
				userData.setName(jsonObj.get("name").getAsString());
				userData.setEmail(jsonObj.get("email").getAsString());
				multiReadRequest.setAttribute("userData", userData);
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				// throw new ResourceNotFoundException("Timeout for this request");

			}

		}

		chain.doFilter(multiReadRequest, response);

	}

}