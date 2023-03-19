package com.example.cloudStorage.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PersonKey.class)
@Table(name = "persons")
@Data
public class Person implements Serializable {
    @Id
    private String name;

    @Id
    private String surname;

    @Id
    private int age;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city_of_living")
    private String cityOfLiving;

    @Override
    public String toString() {
        return String.format("person: %s, %s, %s years old, from %s", name, surname, age, cityOfLiving);
    }

}
