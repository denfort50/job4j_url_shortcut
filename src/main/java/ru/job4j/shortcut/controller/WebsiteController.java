package ru.job4j.shortcut.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.exception.ResourceDoesNotExistException;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Statistics;
import ru.job4j.shortcut.model.Website;
import ru.job4j.shortcut.model.WebsiteDTO;
import ru.job4j.shortcut.service.LinkService;
import ru.job4j.shortcut.service.WebsiteService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class WebsiteController {

    private final WebsiteService websiteService;
    private final LinkService linkService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<WebsiteDTO> registration(@Valid @RequestBody Website website) {
        String login = RandomStringUtils.randomAlphabetic(16);
        String password = RandomStringUtils.randomAlphanumeric(16);
        website.setLogin(login);
        website.setPassword(passwordEncoder.encode(password));
        HttpStatus httpStatus;
        if (websiteService.findByName(website.getName()).isPresent()) {
            website.setRegStatus(false);
            httpStatus = HttpStatus.CONFLICT;
        } else {
            website.setRegStatus(true);
            websiteService.save(website);
            httpStatus = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(new WebsiteDTO(login, password, website.isRegStatus()), httpStatus);
    }

    private Website checkAndGetWebsiteInDb(Authentication authentication) {
        return websiteService.findByLogin(authentication.getName()).orElseThrow(() ->
                new ResourceDoesNotExistException("Website with such a login don't exist in database"
                        + authentication.getName()));
    }

    @PostMapping("/convert")
    public ResponseEntity<String> convert(@Valid @RequestBody Link link,
                                          Authentication authentication) {
        String code = RandomStringUtils.randomAlphanumeric(8);
        link.setCode(code);
        Website websiteInDb = checkAndGetWebsiteInDb(authentication);
        websiteInDb.addLinkToLinks(link);
        linkService.save(link);
        String jsonResponse = "{code: " + code + "}";
        return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> convert(@PathVariable String code) {
        Link link = linkService.findByCode(code).orElseThrow(() ->
                new ResourceDoesNotExistException("Such a code does not exist: " + code));
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(link.getLink())).build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<Statistics>> getStatistics(Authentication authentication) {
        Website websiteInDb = checkAndGetWebsiteInDb(authentication);
        List<Statistics> statistics = linkService.findStatisticsByWebsiteId(websiteInDb.getId());
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

}
