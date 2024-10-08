package com.szabolcs.SpringbootWebshop.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @SequenceGenerator(name = "seqGenUser", sequenceName = "userIdSeq", initialValue = 2000, allocationSize = 1)
    @GeneratedValue(generator = "seqGenUser")
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;

    public User() {
    }

    public User(long id,String firstName, String lastName, String email, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
