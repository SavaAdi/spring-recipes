package com.adisava.springrecipes.repositories;

import com.adisava.springrecipes.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryInterface extends CrudRepository<Category, Long> {
}
