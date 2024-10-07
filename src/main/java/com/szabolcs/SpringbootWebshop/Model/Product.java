package com.szabolcs.SpringbootWebshop.Model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @SequenceGenerator(name = "seqGenProduct", sequenceName = "productIdSeq", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(generator = "seqGenProduct")
    @Column(name = "user_id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price", precision = 2)
    private double price;
    @Column(name = "description")
    private String description;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name = "last_modified")
    @LastModifiedDate
    private LocalDateTime lastModified;

    public Product() {
    }

    public Product(long id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
