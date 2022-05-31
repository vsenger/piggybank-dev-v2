package piggybank.api.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;


@Entity
public class Category extends PanacheEntity {
    String description;

    public Category(){}

    public Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
