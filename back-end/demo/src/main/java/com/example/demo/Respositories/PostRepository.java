package com.example.demo.Respositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.Tables.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
     public Iterable<Post> findByUserId(Long id);

     public Iterable<Post> findAllByOrderByPriceAsc();

     public Iterable<Post> findAllByOrderByPriceDesc();

     public Iterable<Post> findAllByOrderByDateAsc();

     public Iterable<Post> findAllByOrderByDateDesc();

     public Iterable<Post> findAllByNameContaining(String keyword);

     public Iterable<Post> findAll(); // return all posts

     @Query("SELECT c.post.id FROM Category c WHERE c.category.categoryName = :categoryName")
     List<Long> findPostIdsByCategoryName(@Param("categoryName") String categoryName);
}


