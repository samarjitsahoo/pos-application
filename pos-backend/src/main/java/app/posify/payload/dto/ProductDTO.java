package app.posify.payload.dto;

import app.posify.modal.Store;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    private String name;

    private String sku;

    private String description;

    private Double mrp;
    private Double sellingPrice;
    private String brand;
    private String image;

    private CategoryDTO category;

    private Long categoryId;

    private Long storeId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
