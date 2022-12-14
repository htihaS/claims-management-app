package com.cts.mfpe.claim.config;



import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

 	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        System.out.println("inside the dofilter");
        System.out.println(header);
        if (header == null) {
            chain.doFilter(request, response);
            
        }else {
        	  UsernamePasswordAuthenticationToken authentication = authenticate(request);
              SecurityContextHolder.getContext().setAuthentication(authentication);
              chain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken authenticate(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token=token.substring(7); //  
        System.out.println(token);
        if (token != null) {
            Claims user = Jwts.parser().setSigningKey(DatatypeConverter
            							.parseBase64Binary("cfejwcodjdkfd"))
            						.parseClaimsJws(token).getBody();
            System.out.println(user);

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }else{
                return  null;
            }

        }
        return null;
    }
}
