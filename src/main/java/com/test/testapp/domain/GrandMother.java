package com.test.testapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GrandMother.
 */
@Entity
@Table(name = "grand_mother")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrandMother implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "mother_age")
    private Integer motherAge;

    @Column(name = "address")
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMotherName() {
        return motherName;
    }

    public GrandMother motherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Integer getMotherAge() {
        return motherAge;
    }

    public GrandMother motherAge(Integer motherAge) {
        this.motherAge = motherAge;
        return this;
    }

    public void setMotherAge(Integer motherAge) {
        this.motherAge = motherAge;
    }

    public String getAddress() {
        return address;
    }

    public GrandMother address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GrandMother grandMother = (GrandMother) o;
        if(grandMother.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, grandMother.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GrandMother{" +
            "id=" + id +
            ", motherName='" + motherName + "'" +
            ", motherAge='" + motherAge + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
