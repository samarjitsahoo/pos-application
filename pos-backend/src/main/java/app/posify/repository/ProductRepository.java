package app.posify.repository;

import app.posify.modal.Product;
import app.posify.payload.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStoreId(Long storeId);

    @Query(
            "SELECT p FROM Product p " +
                    "WHERE p.store.id = :storeId AND ( " +
                    "LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
                    "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%')) " +
                    "OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :query, '%'))" +
                    ")"
    )
    List<Product> searchByKeyword(@Param("storeId") Long storeId,
                                  @Param("query") String keyword);
}

