package com.example.webapp1a.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webapp1a.model.items.User;
import com.example.webapp1a.repository.items.UserRepository;

@Service
public class UserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //System.out.println("username" + username);

        Optional<User> user = userRepository.findByEmail(username); 

        if(user.isPresent()){
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRol()));

            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getEncodedPassword(), roles);
        }

        return null;
    }
}
