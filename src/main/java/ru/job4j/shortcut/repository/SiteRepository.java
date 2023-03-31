package ru.job4j.shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.Site;

import java.util.List;

public interface SiteRepository extends CrudRepository<Site, Integer> {

    Site findByLogin(String login);

    List<Site> findAll();
}
