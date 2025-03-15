package com.example.demo.Services;

import org.springframework.stereotype.Service;

import com.example.demo.Respositories.CategoryNameRepository;
import com.example.demo.Respositories.CategoryRepository;
import com.example.demo.Tables.Category;
import com.example.demo.Tables.CategoryName;
import com.example.demo.Tables.Post;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final CategoryNameRepository categoryNameRepository;
    public CategoryService(CategoryRepository categoryRepository,CategoryNameRepository categoryNameRepository)
    {
        this.categoryNameRepository = categoryNameRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates and uploads a category/post relation
     * @param categoryName The string name of category i.e "Tech". Must be present in the category_names table
     * @param post Post to relate the category with
     */
    public void uploadCategory(String categoryName,Post post)
    {

        if(categoryName == null)
            return;
        
        // Get the id and name of the category from category_names table
        CategoryName name = categoryNameRepository.findByCategoryName(categoryName);

        // Create the category row (categoryName, post) example -> ("Tech", post id 45)
        Category category = new Category(name, post);

        // Upload the category row into the table
        categoryRepository.save(category);
    }

    /**
     * Finds all the categories that is related to a post
     * @param post Post to find categories for
     * @return List of categories related to post
     */
    public Iterable<Category> findAllByPost(Post post)
    {
        return categoryRepository.findAllByPost(post);
    }

    /**
     * Deletes a category entry from the database
     * @param category The category(id,category_id,post_id) to delete
     */
    public void delete(Category category)
    {
        categoryRepository.delete(category);
    }
}
