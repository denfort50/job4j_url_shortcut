package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Website;
import ru.job4j.shortcut.repository.WebsiteRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    public void save(Website website) {
        websiteRepository.save(website);
    }

    public Optional<Website> findByLogin(String login) {
        return websiteRepository.findByLogin(login);
    }

    public Optional<Website> findByName(String name) {
        return websiteRepository.findByName(name);
    }

}
