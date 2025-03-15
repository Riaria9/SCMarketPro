package com.example.demo.Controllers;

import java.io.IOException;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Services.PostService;
import com.example.demo.Tables.Post;

@RestController
@RequestMapping("/posts")
public class PostController 
{
    private final PostService postService;

    public PostController(PostService postService)
    {
        this.postService = postService;
    }

   
    
    @PostMapping("/upload-post")
    public ResponseEntity<String> uploadPost(
        @RequestParam("user_id") Long userId,
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("price") double price,
        @RequestParam("images") List<MultipartFile>images,
        @RequestParam("categories") List<String> categories) throws IOException 
    {

        try
        {
            postService.uploadPost(title, description, price, userId,images,categories);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok("Post has been uploaded.");
    }

    @DeleteMapping("/delete-post")
    public ResponseEntity<String> deletePost(@RequestParam("user_id") Long userId, @RequestParam("post_id") Long postId)
    {
        try
        {
            postService.deletePost(userId, postId);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Post has been deleted.");
    }
    
    @PutMapping("/update-post")
    public ResponseEntity<String> updatePost(
        @RequestParam("post_id") Long postId,
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("price") double price,
        @RequestParam("images") List<MultipartFile>images,
        @RequestParam("categories") List<String> categories) throws IOException
    {
        try
        {
            postService.updatePost(postId, title, description, price,categories,images);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok("Post has been updated.");
    }

    @GetMapping("/get-post")
    public ResponseEntity<Post> getPost(@RequestParam("post_id") Long postId)
    {
        Post post = postService.getPost(postId);

        if(post == null)
        {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(post);
        }
    }
    
    @GetMapping("/get-all-user-posts")
    public ResponseEntity<Iterable<Post>> getAllUserPosts(@RequestParam("user_id") Long userId)
    {
        Iterable<Post> posts;
        try
        {
            posts = postService.getAllUserPosts(userId);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok(posts);
    }

    
    @GetMapping("/find-posts-name")
    public ResponseEntity<Iterable<Post>> findPostsContaining(@RequestParam("keyword") String keyword) {
        Iterable<Post> posts;
        try {
            posts = postService.findAllPostsByKeyword(keyword);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/sort-posts-price-asc")
    public ResponseEntity<Iterable<Post>> findPostsByPriceAsc() {
        Iterable<Post> posts;
        try {
            posts = postService.findAllByOrderByPriceAsc();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/sort-posts-price-desc")
    public ResponseEntity<Iterable<Post>> findPostsByPriceDesc() {
        Iterable<Post> posts;
        try {
            posts = postService.findAllByOrderByPriceDesc();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/sort-posts-date-asc")
    public ResponseEntity<Iterable<Post>> findPostsByDateAsc() {
        Iterable<Post> posts;
        try {
            posts = postService.findAllByOrderByDateAsc();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/sort-posts-date-desc")
    public ResponseEntity<Iterable<Post>> findPostsByDateDesc() {
        Iterable<Post> posts;
        try {
            posts = postService.findAllByOrderByDateDesc();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/find-posts-category")
    public ResponseEntity<Iterable<Post>> findPostsByCategory(@RequestParam("cat") String cat) {
        Iterable<Post> posts;
        try {
            posts = postService.findAllPostsByCategoryName(cat);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(posts);
    }

}
