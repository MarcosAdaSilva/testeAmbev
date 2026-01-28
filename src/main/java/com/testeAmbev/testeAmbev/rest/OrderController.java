package com.testeAmbev.testeAmbev.rest;

import com.testeAmbev.testeAmbev.dto.CreateOrderRequestDTO;
import com.testeAmbev.testeAmbev.dto.OrderResponseDTO;
import com.testeAmbev.testeAmbev.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(@Valid @RequestBody CreateOrderRequestDTO request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping("/number/{orderNumber}")
    public OrderResponseDTO getOrderByNumber(@PathVariable String orderNumber) {
        return orderService.getOrderByNumber(orderNumber);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }
}