package com.example.demo.Respositories;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.Tables.PostImage;

public interface PostImageRepository extends CrudRepository<PostImage, Long>
{
    Iterable<PostImage> findAllBypost_id(Long post_id);
    
}
