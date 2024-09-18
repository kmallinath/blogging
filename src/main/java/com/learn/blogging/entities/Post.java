package com.learn.blogging.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private  String content;
    @Column(name = "addedDate", columnDefinition = "DATETIME")
    private Date Addeddate;
    private   String imageUrl;
    @ManyToOne
    @JoinColumn(nullable = false,name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(nullable = false,name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER)
    private List<Comment> comments;
}
