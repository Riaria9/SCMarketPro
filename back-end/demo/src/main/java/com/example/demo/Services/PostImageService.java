package com.example.demo.Services;

import org.springframework.stereotype.Service;

import com.example.demo.Respositories.PostImageRepository;
import com.example.demo.Tables.PostImage;

@Service
public class PostImageService
{
    private final PostImageRepository imageRepository;
    
    public PostImageService(PostImageRepository imageRepository)
    {
        this.imageRepository = imageRepository;
    }

    /**
     * Save image of post to database
     * @param image The image to save
     */
    public void uploadImage(PostImage image)
    {
        imageRepository.save(image);
    }
    

    /**
     * Find an PostImage by its ID
     * @param imageId Id of the PostImage
     * @return PostImage if found, or null
     */
    public PostImage findImageById(Long imageId)
    {
        return imageRepository.findById(imageId).orElse(null);
    }

    /**
     * Delete an image from database
     * @param imageId Id of the image to delete
     * @throws Exception Thrown if no image found
     */
    public void deleteImage(Long imageId) throws Exception
    {
        
        // Find the iamge
        PostImage imageToDelete = findImageById(imageId);

        // Delete if found
        if(imageToDelete != null)
        {
            imageRepository.delete(imageToDelete);
        }
        else
            throw new Exception("Image not found.");
    }


    public Iterable<PostImage> findAllByPostId(Long postId)
    {
        return imageRepository.findAllBypost_id(postId);
    }

}
