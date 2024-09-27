package com.learn.blogging.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,
            IOException {

        String requestToken=request.getHeader("Authorization");
        System.out.println(requestToken);
        String username=null,token=null;

        if(request!=null && requestToken!=null && requestToken.startsWith("Bearer"))
        {
             token=requestToken.substring(7);
             try {
                 username = jwtTokenHelper.getUsernameFromToken(token);
             }
             catch (IllegalArgumentException iae)
             {
                 System.out.println("Unable to get JWT token");
             }
             catch (ExpiredJwtException ee)
             {
                 System.out.println("JWT token expired");
             }
             catch (MalformedJwtException me)
             {
                 System.out.println("Invalid JWT");
             }
        }
        else
        {
            System.out.println("Issue with the JWT Token");
        }


        //Once we get the token we will validate

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                if(jwtTokenHelper.validateToken(token,userDetails))
                {
                    //
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                else
                {
                    System.out.println("Invalid jwt token");
                }
        }
        else
        {
            System.out.println("Username is null or ContextHolder is not Null");
        }

        filterChain.doFilter(request,response);




    }
}
