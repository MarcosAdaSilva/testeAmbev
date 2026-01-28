package com.testeAmbev.testeAmbev.service;

import com.testeAmbev.testeAmbev.domain.entity.Order;
import com.testeAmbev.testeAmbev.domain.entity.OrderRepository;
import com.testeAmbev.testeAmbev.domain.entity.enums.OrderStatus;
import com.testeAmbev.testeAmbev.dto.CreateOrderRequestDTO;
import com.testeAmbev.testeAmbev.dto.OrderItemRequestDTO;
import com.testeAmbev.testeAmbev.dto.OrderResponseDTO;
import com.testeAmbev.testeAmbev.dto.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrderService - Teste Unitário")
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServiceResolver productServiceResolver;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Deve criar pedido com sucesso")
    void shouldCreateOrderSuccessfully() {
        OrderItemRequestDTO itemRequest = OrderItemRequestDTO.builder()
                .productId("PROD-001")
                .quantity(10)
                .build();

        CreateOrderRequestDTO request = CreateOrderRequestDTO.builder()
                .items(List.of(itemRequest))
                .build();

        ProductDTO productDTO = ProductDTO.builder()
                .id("PROD-001")
                .name("Cerveja Brahma 350ml")
                .price(new BigDecimal("3.50"))
                .available(true)
                .build();

        when(orderRepository.existsByOrderNumber(anyString())).thenReturn(false);
        when(productServiceResolver.resolveProduct("PROD-001")).thenReturn(productDTO);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        OrderResponseDTO response = orderService.createOrder(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getOrderNumber()).isNotNull().startsWith("ORD-");
        assertThat(response.getStatus()).isEqualTo(OrderStatus.COMPLETED);
        assertThat(response.getItems()).hasSize(1);
        assertThat(response.getItems().get(0).getProductId()).isEqualTo("PROD-001");
        assertThat(response.getItems().get(0).getProductName()).isEqualTo("Cerveja Brahma 350ml");
        assertThat(response.getItems().get(0).getQuantity()).isEqualTo(10);
        assertThat(response.getItems().get(0).getUnitPrice()).isEqualByComparingTo(new BigDecimal("3.50"));
        assertThat(response.getItems().get(0).getSubtotal()).isEqualByComparingTo(new BigDecimal("35.00"));
        assertThat(response.getTotalAmount()).isEqualByComparingTo(new BigDecimal("35.00"));
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getProcessedAt()).isNotNull();

        verify(orderRepository).existsByOrderNumber(anyString());
        verify(productServiceResolver).resolveProduct("PROD-001");
        verify(orderRepository).save(any(Order.class));
    }
}