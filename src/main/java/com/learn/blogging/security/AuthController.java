package com.learn.blogging.security;

import com.learn.blogging.beans.UserDto;
import com.learn.blogging.exceptions.InvalidUserException;
import com.learn.blogging.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {


    @Autowired
    private   JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;




    @PostMapping("/register")
    public ResponseEntity<UserDto>registerNewUser(@RequestBody  UserDto userDto)
    {
        UserDto userDto1=userService.registerUser(userDto);
        return  new ResponseEntity<>(userDto1,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest
            ) throws Exception {
        try {
            this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        UserDetails userDetails= userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token =jwtTokenHelper.generateToken(userDetails);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setToken(token);
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username, password);
try {
    authenticationManager.authenticate(usernamePasswordAuthenticationToken);
}
catch (BadCredentialsException e)
{
    System.out.println("Invalid Details!!!");
    throw  new InvalidUserException("Invalid User Details");
}



    }
}
