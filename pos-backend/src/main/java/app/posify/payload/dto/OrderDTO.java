package app.posify.payload.dto;

import app.posify.domain.PaymentType;
import app.posify.modal.Branch;
import app.posify.modal.Customer;
import app.posify.modal.OrderItem;
import app.posify.modal.User;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private Long branchId;
    private Long customerId;

    private BranchDTO branch;

    private UserDto cashier;

    private Customer customer;

    private PaymentType paymentType;

    private List<OrderItemDTO> items;
}
