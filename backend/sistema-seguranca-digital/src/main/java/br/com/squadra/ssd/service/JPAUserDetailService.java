package br.com.squadra.ssd.service;

import br.com.squadra.ssd.model.User;
import br.com.squadra.ssd.repository.UserRepository;
import br.com.squadra.ssd.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JPAUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(CustomUserDetails::new).get();
    }
}
