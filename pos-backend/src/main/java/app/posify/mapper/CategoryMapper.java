package app.posify.mapper;

import app.posify.modal.Category;
import app.posify.payload.dto.CategoryDTO;

public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .storeId(category.getStore()!=null ? category.getStore().getId() : null)
                .build();
    }
}
