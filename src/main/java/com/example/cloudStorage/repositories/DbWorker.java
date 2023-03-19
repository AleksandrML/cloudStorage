package com.example.cloudStorage.repositories;

import com.example.cloudStorage.models.Person;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class DbWorker {

    private final PersonRepository personRepository;

    public DbWorker(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersonByCity(String city) {
        return personRepository.findByCityOfLivingIgnoreCase(city);
    }

    public List<Person> getPersonByAgeLessThanSorted(int age) {
        return personRepository.findByAgeLessThanOrderByAge(age);
    }

    public Optional<List<Person>> getPersonByNameAndSurname(String name, String surname) {
        return personRepository.findByNameIgnoreCaseAndSurnameIgnoreCase(name, surname);
    }

}
