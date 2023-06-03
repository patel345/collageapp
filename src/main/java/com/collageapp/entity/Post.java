package com.collageapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
        (
           name ="posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"collageName"})}
        )
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "collageName",nullable = false)
    private String collageName;
    @Column(name="collageFee",nullable = false)
    private String collageFee;
    @Column(name="collageStream",nullable = false)
    private String collageStream;
    @Column(name="collageLocation",nullable = false)
    private String collageLocation;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comments=new HashSet<>();

}
