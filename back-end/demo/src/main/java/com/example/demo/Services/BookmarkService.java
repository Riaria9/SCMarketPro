package com.example.demo.Services;

import org.springframework.stereotype.Service;

import com.example.demo.Respositories.BookmarkRepository;
import com.example.demo.Tables.Bookmark;
import com.example.demo.Tables.Post;
import com.example.demo.Tables.User;

@Service
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    private final PostService postService;

    public BookmarkService(BookmarkRepository bookmarkRepository, UserService userService, PostService postService) {
        this.bookmarkRepository = bookmarkRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Iterable<Bookmark> findAll() {
        return bookmarkRepository.findAll();
    }

    public Iterable<Bookmark> findByUserId(Long userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    /**
     * Retrieves a bookmark from the database
     * @param bookmarkId The id of the bookmark you wish to get
     * @return Bookmark if found : else, null
     */
    public Bookmark findByBookmarkId(Long bookmarkId)
    {
        return bookmarkRepository.findById(bookmarkId).orElse(null);
    }

    /**
     * This function will take all the information needed to create a bookmark and upload everything to the database
     * @param userId Id of the user making the post 
     * @param postId Id of the post being bookmarked by the user
     * @throws Exception If user or post is not found
     */
    public void uploadBookmark(Long userId, Long postId) throws Exception
    {
        // Retrieve the user object
        User user = userService.findById(userId);
        if(user == null)
            throw new Exception("User not found");

        // Retrieve the post object
        Post post = postService.findByPostId(postId);
        if (post == null) 
            throw new Exception("Post not found");

        // Create the post object 
        Bookmark bookmark = new Bookmark(user, post);

        // Save the post into database
        bookmarkRepository.save(bookmark);

    }

    /**
     * This function will delete a valid bookmark from the database
     * @param userId User id of the current user
     * @param bookmarkId Bookmark id to be deleted
     * @throws Exception In case bookmark or user dont exist or the current user and the user the bookmark belongs to don't match
     */
    public void deleteBookmark(Long userId, Long bookmarkId) throws Exception
    {
        // Grab the user and post
        User user = userService.findById(userId);
        Bookmark bookmark = this.findByBookmarkId(bookmarkId);

        // Check if post or user were not found in database
        if(user == null)
            throw new Exception("User not found");
        else if(bookmark == null)
            throw new Exception("Bookmark not found");

        // Check if the post owner matches the given user id
        if(bookmark.getUser() != user)
            throw new Exception("User does not match bookmark owner");

        // Delete the post from database
        bookmarkRepository.delete(bookmark);

    }

}
