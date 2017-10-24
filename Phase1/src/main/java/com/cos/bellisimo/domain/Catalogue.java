package com.cos.bellisimo.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Catalogue.
 */
@Entity
@Table(name = "catalogue")
public class Catalogue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "onspecial")
    private Boolean onspecial;

    @Column(name = "special_price")
    private Double specialPrice;

    @Column(name = "path")
    private String path;

    @Column(name = "valid")
    private Boolean valid;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Catalogue title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Catalogue description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public Catalogue price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isOnspecial() {
        return onspecial;
    }

    public Catalogue onspecial(Boolean onspecial) {
        this.onspecial = onspecial;
        return this;
    }

    public void setOnspecial(Boolean onspecial) {
        this.onspecial = onspecial;
    }

    public Double getSpecialPrice() {
        return specialPrice;
    }

    public Catalogue specialPrice(Double specialPrice) {
        this.specialPrice = specialPrice;
        return this;
    }

    public void setSpecialPrice(Double specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getPath() {
        return path;
    }

    public Catalogue path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isValid() {
        return valid;
    }

    public Catalogue valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Catalogue catalogue = (Catalogue) o;
        if (catalogue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), catalogue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Catalogue{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", onspecial='" + isOnspecial() + "'" +
            ", specialPrice='" + getSpecialPrice() + "'" +
            ", path='" + getPath() + "'" +
            ", valid='" + isValid() + "'" +
            "}";
    }
}
