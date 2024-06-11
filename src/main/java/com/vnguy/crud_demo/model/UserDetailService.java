package com.vnguy.crud_demo.model;

import com.vnguy.crud_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User userObj = user.get();
            return new User(
                    userObj.getUsername(),
                    userObj.getPassword(),
                    getRole(userObj)
            );
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private User.UserRole getRole(User user) {
        if (user.getRole() == null) {
            return User.UserRole.USER;
        }
        return user.getRole();
    }
}
