package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Website;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class WebsiteDetailsServiceImpl implements UserDetailsService {

    private WebsiteService websiteService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Website website = websiteService.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
        return new User(website.getLogin(), website.getPassword(), emptyList());
    }
}
