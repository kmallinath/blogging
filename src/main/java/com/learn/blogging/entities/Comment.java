package com.learn.blogging.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;

    private String user;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;


}
