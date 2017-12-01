package com.fileRepository.fileRepository.entity;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class File {

    private Long id;
    private String link;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
