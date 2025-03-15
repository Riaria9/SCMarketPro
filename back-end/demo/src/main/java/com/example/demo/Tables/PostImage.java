package com.example.demo.Tables;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "posts_images")
@JsonIgnoreProperties({"post"}) // Does not populate json response with data about post
public class PostImage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "link")
    private String link;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
    
    public PostImage(){

    }

    public PostImage(String link,Post post)
    {
        this.link = link;
        this.post = post;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Post getPost_id() {
        return this.post;
    }

    public void setPost_id(Post post_id) {
        this.post = post_id;
    }
    

}
