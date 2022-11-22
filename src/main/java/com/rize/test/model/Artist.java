package com.rize.test.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "artists",
        indexes = {
                @Index(name="i_category", columnList = "category")
        }
)
public class Artist implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "created_at", updatable = false, nullable = false, columnDefinition = "timestamp with time zone")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp with time zone")
    @UpdateTimestamp
    private Instant updatedAt;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "birthday", nullable = false)
    private Date birthday;
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "notes")
    private String notes;

    public Integer getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getCategory() {
        return category;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getNotes() {
        return notes;
    }

    public Artist(){

    }
    public Artist(String firstName, String lastName, Date birthday, String email, String category, String middleName, String notes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.category = category;
        this.email = email;
        this.middleName = middleName;
        this.notes = notes;
    }
}
