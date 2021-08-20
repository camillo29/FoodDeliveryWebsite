package com.FoodDelivery.FoodDelivery.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne
    private Person person;

    @ManyToMany
    private Set<Role> roles = new HashSet<Role>();

    public User(String email, String password, Person person, Role role){
        this.username = email;
        this.password = password;
        this.person = person;
        addToSet(role);
    }
    public User(){}
    public void addToSet(Role role){
        roles.add(role);
    }
    public void removeFromSet(Role role){
        roles.remove(role);
    }
    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    //Getters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Person getPerson() {
        return person;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}

