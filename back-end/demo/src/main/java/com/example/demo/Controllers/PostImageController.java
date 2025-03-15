package com.example.demo.Controllers;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Services.PostImageService;
import com.example.demo.Services.PostService;
import com.example.demo.Tables.PostImage;


@RestController
@RequestMapping("/images")
public class PostImageController
{
    private final PostImageService imageService;
    private final PostService postService;

    public PostImageController(PostImageService imageService,PostService postService)
    {
        this.imageService = imageService;
        this.postService = postService;

    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<String> deleteImage(@RequestParam Long imageId)
    {
        try 
        {
            imageService.deleteImage(imageId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }


        return ResponseEntity.ok("Image deleted");
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("post_id") Long postId, @RequestParam("image") MultipartFile image)
    {
        try
        {
            postService.uploadImageToPost(image, postId);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("Image posted");
    }

    @GetMapping("/get-images")
    public ResponseEntity<Iterable<PostImage>> getImages(@RequestParam("post_id") Long postId)
    {
        Iterable<PostImage> images = imageService.findAllByPostId(postId);

        if(images == null)
        {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        else
        {
            return ResponseEntity.ok(images);
        }
        
    }
}
