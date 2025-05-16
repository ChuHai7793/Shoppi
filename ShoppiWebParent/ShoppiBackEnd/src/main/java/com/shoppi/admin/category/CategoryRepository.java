package com.shoppi.admin.category;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shoppi.common.entity.Category;


public interface CategoryRepository extends CrudRepository<Category, Integer>,PagingAndSortingRepository<Category, Integer> {
    Category findByName(String name);
}
