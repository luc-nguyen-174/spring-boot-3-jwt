package com.example.securityspringboot3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserInfo> users;

    public Role(String roles) {
        this.name = roles;
    }
}


