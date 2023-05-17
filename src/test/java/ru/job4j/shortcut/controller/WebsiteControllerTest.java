package ru.job4j.shortcut.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Website;
import ru.job4j.shortcut.model.WebsiteDTO;
import ru.job4j.shortcut.service.LinkService;
import ru.job4j.shortcut.service.WebsiteService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WebsiteControllerTest {

    private final WebsiteService websiteService = Mockito.mock(WebsiteService.class);
    private final LinkService linkService = Mockito.mock(LinkService.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    private final WebsiteController websiteController =
            new WebsiteController(websiteService, linkService, passwordEncoder);

    @Test
    void whenRegisterThenReturnsStatusCodeCreated() {
        Website website = new Website(1, "name", "login", "password", true,
                new ArrayList<>(List.of(new Link())));
        ResponseEntity<WebsiteDTO> response = websiteController.registration(website);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

}