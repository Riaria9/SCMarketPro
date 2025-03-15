package com.example.demo.Respositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Tables.CategoryName;



public interface CategoryNameRepository extends CrudRepository<CategoryName, Long>
{
    public CategoryName findByCategoryName(String categoryName);
}
