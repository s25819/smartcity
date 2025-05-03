package pl.edu.pjatk.s25819.smartcity.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import static jakarta.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class BaseEntity<T> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected T id;

    protected T getId() {
        return id;
    }
}
