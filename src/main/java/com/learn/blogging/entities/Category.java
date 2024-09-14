package com.learn.blogging.entities;

import jakarta.persistence.*;
import javafx.geometry.Pos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private  int id;
     @Column(name="category_title",nullable = false)
     private String title;

     private String description;

     @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
     private List<Post> postList;
}
