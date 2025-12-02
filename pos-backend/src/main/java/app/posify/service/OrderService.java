package app.posify.service;

import app.posify.domain.OrderStatus;
import app.posify.domain.PaymentType;
import app.posify.payload.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO) throws Exception;
    OrderDTO getOrderById(Long id) throws Exception;
    List<OrderDTO> getOrdersByBranch(Long branchId,
                                     Long customerId,
                                     Long cashierId,
                                     PaymentType paymentType,
                                     OrderStatus status) throws Exception;
    List<OrderDTO> getOrderByCashier(Long cashierId);
    void deleteOrder(Long id) throws Exception;
    List<OrderDTO> getTodayOrdersByBranch(Long branchId) throws Exception;
    List<OrderDTO> getOrdersByCustomerId(Long customerId) throws Exception;
    List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) throws Exception;
}
