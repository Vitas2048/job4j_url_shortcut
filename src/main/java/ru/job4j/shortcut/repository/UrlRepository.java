package ru.job4j.shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.Url;

import java.util.List;

public interface UrlRepository extends CrudRepository<Url, Integer> {

    List<Url> findAll();

    Url findByCode(String code);
}
