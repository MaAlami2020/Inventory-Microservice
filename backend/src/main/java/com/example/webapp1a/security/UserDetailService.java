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

<<<<<<< HEAD
import com.example.webapp1a.model.items.User;
import com.example.webapp1a.repository.items.UserRepository;
=======
import com.example.webapp1a.model.User;
import com.example.webapp1a.repository.UserRepo;
>>>>>>> 9d8b749fcae5bf7dc1f03fb4e031dc151bd87b91

@Service
public class UserDetailService implements UserDetailsService{

    @Autowired
<<<<<<< HEAD
    private UserRepository userRepository;
=======
    private UserRepo userRepository;

>>>>>>> 9d8b749fcae5bf7dc1f03fb4e031dc151bd87b91
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        System.out.println("username " + username);

		Optional<User> user = userRepository.findByUsername(username);

<<<<<<< HEAD
        if(user.isPresent()){
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRol()));

            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getEncodedPassword(), roles);
        }

        return null;
=======
		if(!user.isPresent()){
			new UsernameNotFoundException("brand.html");
		}
    
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRol()));

		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), 
				user.get().getPassword(), roles);

>>>>>>> 9d8b749fcae5bf7dc1f03fb4e031dc151bd87b91
    }
}
