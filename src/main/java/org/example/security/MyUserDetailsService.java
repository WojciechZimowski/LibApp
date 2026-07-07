package org.example.security;

import org.example.models.User;
import org.example.repos.UserRepoInterface;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepoInterface userRepo;

    public MyUserDetailsService(UserRepoInterface userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
        String roleName = user.getRole().startsWith("ROLE_")?user.getRole():"ROLE_"+user.getRole();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
