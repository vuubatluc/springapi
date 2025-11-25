package com.vu.springapi.mapper;

import com.vu.springapi.dto.request.CategoryRequest;
import com.vu.springapi.dto.response.CategoryResponse;
import com.vu.springapi.model.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-26T02:50:57+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( request.getName() );
        category.description( request.getDescription() );

        return category.build();
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setId( category.getId() );
        categoryResponse.setName( category.getName() );
        categoryResponse.setDescription( category.getDescription() );

        return categoryResponse;
    }

    @Override
    public void updateCategory(Category category, CategoryRequest request) {
        if ( request == null ) {
            return;
        }

        category.setName( request.getName() );
        category.setDescription( request.getDescription() );
    }
}
