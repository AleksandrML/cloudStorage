package com.example.cloudStorage.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("login")
    private String username;

    private String password;

}
