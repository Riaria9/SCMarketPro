package com.example.demo.Respositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Tables.Bookmark;

public interface BookmarkRepository extends CrudRepository<Bookmark, Long> {
    public Iterable<Bookmark> findByUserId(Long userId);
}
