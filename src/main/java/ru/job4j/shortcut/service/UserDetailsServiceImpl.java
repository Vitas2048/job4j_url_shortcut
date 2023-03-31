package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private SiteRepository sites;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = sites.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getLogin(), user.getPassword(), Collections.emptyList());
    }
}
