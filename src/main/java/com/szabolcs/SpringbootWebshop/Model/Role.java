package com.szabolcs.SpringbootWebshop.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @SequenceGenerator(name = "seqGenRole", sequenceName = "roleIdSeq", initialValue = 3000, allocationSize = 1)
    @GeneratedValue(generator = "seqGenRole")
    @Column(name = "role_id")
    private long id;

    @Column(name = "role")
    private String role;

    public Role() {
    }

    public Role(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return role;
    }

    public void setName(String role) {
        this.role = role;
    }
}
