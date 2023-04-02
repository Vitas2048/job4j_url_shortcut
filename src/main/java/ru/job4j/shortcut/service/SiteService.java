package ru.job4j.shortcut.service;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SiteService {

    private SiteRepository siteRepository;

    public Site save(Site site) {
        return siteRepository.save(site);
    }

    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    public Map<?, ?> toMapByIdLogin(List<Site> list) {
        var map = new HashMap<>();
        list.forEach(p -> map.put(p.getId(), p.getSite()));
        return map;
    }


    public <T> String toJson(T object) {
        var gson = new Gson();
        return gson.toJson(object);
    }
}
