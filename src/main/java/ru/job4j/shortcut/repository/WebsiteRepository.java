package ru.job4j.shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.Website;

import java.util.Optional;

public interface WebsiteRepository extends CrudRepository<Website, Integer> {

    Optional<Website> findByLogin(String login);

    Optional<Website> findByName(String name);
}
