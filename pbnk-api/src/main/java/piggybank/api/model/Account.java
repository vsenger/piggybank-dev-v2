package piggybank.api.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;


@Entity
public class Account extends PanacheEntity {
    String name;

    public Account(){}

    public Account(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
