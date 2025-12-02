package app.posify.mapper;

import app.posify.modal.Order;
import app.posify.payload.dto.OrderDTO;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .branchId(order.getBranch().getId())
                .cashier(UserMapper.toDTO(order.getCashier()))
                .customer(order.getCustomer())
                .paymentType(order.getPaymentType())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream()
                        .map(OrderItemMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();

    }

}
