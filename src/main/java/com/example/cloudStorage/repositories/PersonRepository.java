package com.example.cloudStorage.repositories;

import com.example.cloudStorage.models.Person;
import com.example.cloudStorage.models.PersonKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, PersonKey> {

    List<Person> findByCityOfLivingIgnoreCase(String city);

    List<Person> findByAgeLessThanOrderByAge(int age);

    Optional<List<Person>> findByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);

}
