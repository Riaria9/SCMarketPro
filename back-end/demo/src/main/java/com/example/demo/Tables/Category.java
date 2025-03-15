package com.example.demo.Tables;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "categories")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;   

    @ManyToOne
    @JoinColumn(name = "category")
    private CategoryName category;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Category()
    {
        
    }

    public Category(CategoryName category, Post post)
    {
        this.category = category;
        this.post = post;
    }


}
