package com.testeAmbev.testeAmbev.service;

import com.testeAmbev.testeAmbev.domain.entity.Order;
import com.testeAmbev.testeAmbev.domain.entity.OrderItem;
import com.testeAmbev.testeAmbev.domain.entity.OrderRepository;
import com.testeAmbev.testeAmbev.domain.entity.enums.OrderStatus;
import com.testeAmbev.testeAmbev.domain.entity.enums.ProductSource;
import com.testeAmbev.testeAmbev.dto.*;
import com.testeAmbev.testeAmbev.infrastructure.exception.DuplicateOrderException;
import com.testeAmbev.testeAmbev.infrastructure.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceResolver productServiceResolver;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderRequestDTO request) {
        String orderNumber = generateOrderNumber();
        validateOrderUniqueness(orderNumber);

        Order order = buildOrder(orderNumber, request);
        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrder(Long orderId) {
        Order order = findOrderById(orderId);
        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderByNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
        return mapToResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Order buildOrder(String orderNumber, CreateOrderRequestDTO request) {
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        request.getItems().forEach(itemRequest -> {
            OrderItem orderItem = buildOrderItem(itemRequest);
            order.addItem(orderItem);
        });

        order.calculateTotal();
        order.setStatus(OrderStatus.COMPLETED);
        order.setProcessedAt(LocalDateTime.now());

        return order;
    }

    private OrderItem buildOrderItem(OrderItemRequestDTO request) {
        ProductDTO product = productServiceResolver.resolveProduct(request.getProductId());

        OrderItem orderItem = OrderItem.builder()
                .productId(product.getId())
                .productName(product.getName())
                .quantity(request.getQuantity())
                .unitPrice(product.getPrice())
                .source(determineProductSource(product.getId()))
                .build();

        orderItem.calculateSubtotal();
        return orderItem;
    }

    private ProductSource determineProductSource(String productId) {
        if (productId.startsWith("PROD-")) {
            return ProductSource.INTERNAL;
        } else if (productId.startsWith("EXT-A-")) {
            return ProductSource.EXTERNAL_A;
        } else if (productId.startsWith("EXT-B-")) {
            return ProductSource.EXTERNAL_B;
        }
        return ProductSource.INTERNAL;
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void validateOrderUniqueness(String orderNumber) {
        if (orderRepository.existsByOrderNumber(orderNumber)) {
            throw new DuplicateOrderException(orderNumber);
        }
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private OrderResponseDTO mapToResponse(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .items(mapItemsToResponse(order.getItems()))
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .processedAt(order.getProcessedAt())
                .build();
    }

    private List<OrderItemResponseDTO> mapItemsToResponse(List<OrderItem> items) {
        return items.stream()
                .map(item -> OrderItemResponseDTO.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .source(item.getSource())
                        .build())
                .collect(Collectors.toList());
    }
}