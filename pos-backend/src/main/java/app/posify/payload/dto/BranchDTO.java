package app.posify.payload.dto;

import app.posify.modal.Store;
import app.posify.modal.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    private List<String> workingDays;

    private LocalTime openTime;
    private LocalTime closeTime;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private StoreDTO store;

    private Long storeId;

    private UserDto manager;

}
