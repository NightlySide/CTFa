package io.nightlyside.enstabretagne.ctfa;

import io.nightlyside.enstabretagne.ctfa.entities.User;
import io.nightlyside.enstabretagne.ctfa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user_username = userRepository.findByUsername(username);
        User user_email = userRepository.findByEmail(username);

        if(user_username == null && user_email == null) {
            throw new UsernameNotFoundException("Impossible de trouver l'utilisateur : " + username);
        } else if (user_email != null)
            return new UserDetailsImpl(user_email);
        else
            return new UserDetailsImpl(user_username);
    }
}
