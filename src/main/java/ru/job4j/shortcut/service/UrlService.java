package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Url;
import ru.job4j.shortcut.repository.UrlRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class UrlService {

    private UrlRepository repository;

    public Url save(Url url) {
        return repository.save(url);
    }

    public List<Url> findAll() {
        return repository.findAll();
    }

    public Url findByCode(String code) {
        return repository.findByCode(code);
    }
}
