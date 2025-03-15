package com.example.demo.Controllers;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.BookmarkService;
import com.example.demo.Tables.Bookmark;

@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/all-bookmarks")
    public Iterable<Bookmark> findAll() {
        return bookmarkService.findAll();
    }

    @GetMapping("/all-user-bookmarks")
    public Iterable<Bookmark> findByUserId(@RequestParam("user_id") Long userId) {
        return bookmarkService.findByUserId(userId);
    }

    @PostMapping("/upload-bookmark")
    public ResponseEntity<String> uploadBookmark(
        @RequestParam("user_id") Long userId,
        @RequestParam("post_id") Long postId) throws IOException 
    {

        try
        {
            bookmarkService.uploadBookmark(userId, postId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok("Bookmark has been uploaded.");
    }

    @DeleteMapping("/delete-bookmark")
    public ResponseEntity<String> deleteBookmark(
        @RequestParam("user_id") Long userId, 
        @RequestParam("bookmark_id") Long bookmarkId)
    {
        try
        {
            bookmarkService.deleteBookmark(userId, bookmarkId);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Bookmark has been deleted.");
    }
}
