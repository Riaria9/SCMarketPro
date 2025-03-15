package com.example.demo.Respositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Tables.Category;
import com.example.demo.Tables.Post;

public interface CategoryRepository extends CrudRepository<Category, Long>
{
    public Iterable<Category> findAllByPost(Post Post);
}
