package com.learn.blogging.security;

import com.learn.blogging.entities.User;
import com.learn.blogging.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByUsername(username);
        if(user==null)
        {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User is not found");
        }

        UserDetails userDetails=new CustomUserDetails(user);
        return  userDetails;

    }
}
