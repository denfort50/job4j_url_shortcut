package ru.job4j.shortcut.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "websites")

public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NotBlank(message = "Site name must be not empty")
    private String name;

    private String login;

    private String password;

    @Column(name = "reg_status")
    private boolean regStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "website")
    List<Link> links = new ArrayList<>();

    public void addLinkToLinks(Link link) {
        links.add(link);
        link.setWebsite(this);
    }
}
