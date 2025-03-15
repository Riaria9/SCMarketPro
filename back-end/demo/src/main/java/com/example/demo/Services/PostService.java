package com.example.demo.Services;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.AWS.S3Service;
import com.example.demo.Respositories.PostRepository;
import com.example.demo.Tables.Category;
import com.example.demo.Tables.Post;
import com.example.demo.Tables.PostImage;
import com.example.demo.Tables.User;


@Service
public class PostService 
{
    private final PostRepository postRepository;
    private final UserService userService;
    private final PostImageService postImageService;
    private final S3Service s3Service;
    private final CategoryService categoryService;

    public PostService(PostRepository postRepository,UserService userService, PostImageService postImageService,S3Service s3Service, CategoryService categoryService) 
    {
        this.postRepository = postRepository;
        this.userService = userService;
        this.postImageService = postImageService;
        this.s3Service = s3Service;
        this.categoryService = categoryService;
    }

    
    /**
     * Retrieves a post from the database
     * @param postId The id of the post you wish to get
     * @return Post if found : else, null
     */
    public Post findByPostId(Long postId)
    {
        return postRepository.findById(postId).orElse(null);
    }

    /**
     * This function will take all the information about a post and upload everything to the database
     * @param title Post title
     * @param description Post description
     * @param price Price of product
     * @param userId Id of the user making the post 
     * @param images Images of product
     * @param categories Categories that this post will belong to
     * @throws Exception If user is not found
     */
    public void uploadPost(String title, String description, double price,Long userId,List<MultipartFile> images,List<String> categories) throws Exception
    {
        // Retrieve the user object
        User user = userService.findById(userId);
        if(user == null)
            throw new Exception("User not found");

        // Create the post object 
        Post post = new Post(description,price,title,user,LocalDateTime.now());

        // Save the post into database
        postRepository.save(post);

        // Iterate all images 
        for(MultipartFile image : images)
        {
            // If file has no name, dont upload
            if(image == null ||image.getOriginalFilename() == null || image.getOriginalFilename().trim().equals(""))
                continue;
            
            // upload image to S3 bucket and retrieve the link
            String link = s3Service.putObject("scmarketplace", image);

            // Create an image post object (Row in the post_images table that has a post id and link to an image for that post)
            PostImage postImage = new PostImage(link,post);

            // Save the image to the database
            postImageService.uploadImage(postImage);
        }

        if(categories.isEmpty())
            return;

        // Iterate through all category strings
        for(String category : categories)
        {
            // Upload the post to the category table for all the categories this post belongs to 
            categoryService.uploadCategory(category, post);
        }


       
    }

    /**
     * Will delete a valid post from the database
     * @param userId User id to which the post belongs to 
     * @param postId The id of the post to delete
     * @throws Exception Incase post or user dont exist or post owner != userId
     */
    public void deletePost(Long userId,Long postId) throws Exception
    {
        // Grab the user and post
        User user = userService.findById(userId);
        Post post = this.findByPostId(postId);

        // Check if post or user were not found in database
        if(user == null)
            throw new Exception("User not found");
        else if(post == null)
            throw new Exception("Post not found");

        // Check if the post owner matches the given user id
        if(post.getUser() != user)
            throw new Exception("User does not match post owner");

        // Delete the post from database
        postRepository.delete(post);

    }

    /**
     * Uploads a single image for a post
     * @param image Image to upload
     * @param postId Id of the post that the image belongs to 
     * @throws Exception If post not found.
     */
    public void uploadImageToPost(MultipartFile image,Long postId) throws Exception
    {
        // Grab the post
        Post post = this.findByPostId(postId);

        if(post == null)
            throw new Exception("Post not found");

        // upload image to S3 bucket and retrieve the link
        String link = s3Service.putObject("scmarketplace", image);

        // Create an image post object (Row in the post_images table that has a post id and link to an image for that post)
        PostImage postImage = new PostImage(link,post);

        // Save the image to the database
        postImageService.uploadImage(postImage);
    }

    /**
     * Updates information of a post
     * @param postId Id of the post to update
     * @param title New title
     * @param description New description
     * @param price New Price
     * @throws Exception If post not found
     */
    public void updatePost(Long postId,String title, String description,double price,List<String> categories,List<MultipartFile> images) throws Exception
    {
        // Find the post
        Post post = this.findByPostId(postId);

        // Throw error if not found
        if(post == null)
            throw new Exception("Post not found");
        
        // Update post
        post.setDescription(description);
        post.setName(title);
        post.setPrice(price);

        // Save back into database
        postRepository.save(post);

        // Get all the categories that this post belonged to 
        Iterable<Category> categoriesObjects = categoryService.findAllByPost(post);

        // Iterate all categories of post
        for(Category category : categoriesObjects)
        {
            // Delete the category
            if(category != null)
                categoryService.delete(category);
        }

        // Iterate through all new category strings
        for(String category : categories)
        {
            // Upload the post to the category table for all the categories this post belongs to 
            categoryService.uploadCategory(category, post);
        }
        
        // Get all the old images and delete them
        Iterable<PostImage> oldImages = postImageService.findAllByPostId(postId);
        for(PostImage image  :oldImages)
        {
            postImageService.deleteImage(image.getId());
        }

        // Iterate all new images and upload them 
        for(MultipartFile image : images)
        {
            // If file has no name, dont upload
            if(image == null ||image.getOriginalFilename() == null || image.getOriginalFilename().trim().equals(""))
                continue;
            
            // upload image to S3 bucket and retrieve the link
            String link = s3Service.putObject("scmarketplace", image);

            // Create an image post object (Row in the post_images table that has a post id and link to an image for that post)
            PostImage postImage = new PostImage(link,post);

            // Save the image to the database
            postImageService.uploadImage(postImage);
        }

    }

    /**
     * Finds a single post 
     * @param postId The id of the post to find
     * @return Post or null if not found
     */
    public Post getPost(Long postId)
    {
        return postRepository.findById(postId).orElse(null);
    }
    /**
     * Finds all the posts belonging to a user
     * @param userId The id of the user
     * @return Lists of posts, or null
     */
    public Iterable<Post> getAllUserPosts(Long userId)
    {
        return postRepository.findByUserId(userId);
    }

    public Iterable<Post> findAllPostsByKeyword(String keyword) {
        return postRepository.findAllByNameContaining(keyword);
    }

    public Iterable<Post> findAllByOrderByPriceAsc() {
        return postRepository.findAllByOrderByPriceAsc();
    }

    public Iterable<Post> findAllByOrderByPriceDesc() {
        return postRepository.findAllByOrderByPriceDesc();
    }

    public Iterable<Post> findAllByOrderByDateAsc() {
        return postRepository.findAllByOrderByDateAsc();
    }

    public Iterable<Post> findAllByOrderByDateDesc() {
        return postRepository.findAllByOrderByDateDesc();
    }

    public Iterable<Post> findAllPostsByCategoryName(String categoryName) {
        List<Long> postIds = postRepository.findPostIdsByCategoryName(categoryName);
        return postRepository.findAllById(postIds);
    }
}

