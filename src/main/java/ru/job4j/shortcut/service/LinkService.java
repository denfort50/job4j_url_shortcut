package ru.job4j.shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Link;
import ru.job4j.shortcut.model.Statistics;
import ru.job4j.shortcut.repository.LinkRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    public void save(Link link) {
        linkRepository.save(link);
    }

    public Optional<Link> findByCode(String code) {
        Optional<Link> link = linkRepository.findByCode(code);
        link.ifPresent(linkCode -> linkRepository.updateLinkCount(linkCode.getId()));
        return link;
    }

    public List<Statistics> findStatisticsByWebsiteId(int websiteId) {
        List<Statistics> statisticsList = new ArrayList<>();
        List<Link> links = linkRepository.findAllByWebsiteId(websiteId);
        links.forEach(link -> statisticsList.add(new Statistics(link.getLink(), link.getCount())));
        return statisticsList;
    }
}
