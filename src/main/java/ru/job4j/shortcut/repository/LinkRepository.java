package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.shortcut.model.Link;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends CrudRepository<Link, Integer> {

    @Transactional
    @Modifying
    @Query("update Link l set l.count = l.count + 1 where l.id = :id")
    void updateLinkCount(@Param("id") int id);

    Optional<Link> findByCode(String code);

    List<Link> findAllByWebsiteId(int id);
}
