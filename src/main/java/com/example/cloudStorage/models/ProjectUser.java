package com.example.cloudStorage.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class ProjectUser implements Serializable {
    @Id
    private String username;

    private String password;

}
