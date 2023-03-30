package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.List;
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
}
