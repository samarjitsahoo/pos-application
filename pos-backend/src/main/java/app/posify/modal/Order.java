package app.posify.modal;

import app.posify.domain.PaymentType;
import com.razorpay.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double totalAmount;

    private LocalDateTime createdAt;

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private User cashier;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "order",  cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private PaymentType paymentType;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
