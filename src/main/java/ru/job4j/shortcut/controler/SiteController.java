package ru.job4j.shortcut.controler;


import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.model.Url;
import ru.job4j.shortcut.service.SiteService;
import ru.job4j.shortcut.service.UrlService;

import java.util.HashMap;

@RestController
@RequestMapping("/sites")
@AllArgsConstructor
public class SiteController {
    private SiteService sites;

    private UrlService urls;

    private BCryptPasswordEncoder encoder;

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@RequestBody Url url) {
        var code = RandomStringUtils.randomAlphanumeric(8);
        url.setCode(code);
        urls.save(url);
        var body = new HashMap<>() {{
            put("code", code);
        }}.toString();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        if (urls.incrementIfPresentByCode(code)) {
            return ResponseEntity
                    .status(HttpStatus.FOUND).build();
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody Site site) {
        var password = RandomStringUtils.randomAlphanumeric(7);
        var login = RandomStringUtils.randomAlphanumeric(7);
        site.setLogin(login);
        site.setPassword(encoder.encode(password));
        var body = new HashMap<>() {{
            put("registration", false);
            put("login", "");
            put("password", "");
        }}.toString();
        try {
            sites.save(site);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(body.length())
                    .body(body);
        }
        body = new HashMap<>() {{
            put("registration", true);
            put("login", login);
            put("password", password);
        }}.toString();
        return ResponseEntity.
                status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
    }

    @GetMapping("/all")
    public ResponseEntity<String> findAll() {
        var body = sites.toJson(sites.toMapByIdLogin(sites.findAll()));
        return ResponseEntity.status(HttpStatus.OK)
                .header("List of sites", "Successful")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
    }

    @GetMapping("/statistic")
    public ResponseEntity<String> getStatistic() {
        var body = sites.toJson(urls.mapBuUrlCount(urls.findAll()));
        return ResponseEntity.status(HttpStatus.OK)
                .header("Statistic", "Successful")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(body.length())
                .body(body);
    }
}
