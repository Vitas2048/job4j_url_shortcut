package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.shortcut.model.Url;
import ru.job4j.shortcut.repository.UrlRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional
    public void incrementByCode(String code) {
        repository.incrementByCode(code);
    }

    @Transactional
    public boolean incrementIfPresentByCode(String code) {
        if (findByCode(code) != null) {
            incrementByCode(code);
            return true;
        }
        return false;
    }

    public Map<?, ?> mapBuUrlCount(List<Url> list) {
        var map = new HashMap<>();
        list.forEach(p -> map.put(p.getUrl(), p.getCount()));
        return map;
    }
}
