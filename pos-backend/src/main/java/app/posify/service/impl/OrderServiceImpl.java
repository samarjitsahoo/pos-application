package app.posify.service.impl;

import app.posify.domain.OrderStatus;
import app.posify.domain.PaymentType;
import app.posify.mapper.OrderMapper;
import app.posify.modal.*;
import app.posify.payload.dto.OrderDTO;
import app.posify.repository.OrderItemRepository;
import app.posify.repository.OrderRepository;
import app.posify.repository.ProductRepository;
import app.posify.service.OrderService;
import app.posify.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final UserService userService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transient
    public OrderDTO createOrder(OrderDTO orderDTO) throws Exception {

        User cashier = userService.getCurrentUser();
        Branch branch = cashier.getBranch();

        if (branch == null) {
            throw new Exception("cashier's branch not found");
        }

        // 1. Create order
        Order order = Order.builder()
                .branch(branch)
                .cashier(cashier)
                .customer(orderDTO.getCustomer())
                .paymentType(orderDTO.getPaymentType())
                .build();

        // 2. Convert DTO items to OrderItem entities
        List<OrderItem> orderItems = orderDTO.getItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(itemDto.getQuantity())
                            .price(product.getSellingPrice() * itemDto.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());

        // 3. Set items + total
        double total = orderItems.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();

        order.setItems(orderItems);
        order.setTotalAmount(total);

        // 4. Save only once (cascade inserts items)
        Order savedOrder = orderRepository.save(order);

        return OrderMapper.toDTO(savedOrder);
    }


    @Override
    public OrderDTO getOrderById(Long id) throws Exception {
        return orderRepository.findById(id)
                .map(OrderMapper::toDTO)
                .orElseThrow(
                () -> new Exception("order not found with id " + id)
        );
    }

    @Override
    public List<OrderDTO> getOrdersByBranch(Long branchId,
                                            Long customerId,
                                            Long cashierId,
                                            PaymentType paymentType,
                                            OrderStatus status) throws Exception {
        return orderRepository.findByBranchId(branchId).stream()
                .filter(order -> customerId==null ||
                        (order.getCustomer()!=null &&
                                order.getCustomer().getId().equals(customerId)))
                .filter(order -> cashierId==null ||
                        order.getCashier()!=null &&
                        order.getCashier().getId().equals(cashierId))
                .filter(order -> paymentType==null ||
                        order.getPaymentType()==paymentType)
                .map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrderByCashier(Long cashierId) {
        return orderRepository.findByCashierId(cashierId).stream()
                .map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new Exception("order not found with id " + id)
        );
        orderRepository.delete(order);

    }

    @Override
    public List<OrderDTO> getTodayOrdersByBranch(Long branchId) throws Exception {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();


        return orderRepository.findByBranchIdAndCreatedAtBetween(
                branchId, start, end
        ).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) throws Exception {
        return orderRepository.findByCustomerId(customerId).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) throws Exception {
        return orderRepository.findTop5ByBranchIdOrderByCreatedAtDesc(branchId).stream().map(OrderMapper::toDTO).collect(Collectors.toList());
    }
}
