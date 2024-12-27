package com.rajesh;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Wiki_Media")
public class Wikimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Lob
    private String wikiData;

    public void setId(Long id) {
        this.id = id;
    }

    public void setWikiData(String wikiData) {
        this.wikiData = wikiData;
    }

    public Long getId() {
        return id;
    }

    public String getWikiData() {
        return wikiData;
    }
}
