package inflearn.spring.core.order.service;

import inflearn.spring.core.order.Order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);

}
