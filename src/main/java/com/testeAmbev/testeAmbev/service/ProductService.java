package com.testeAmbev.testeAmbev.service;



import com.testeAmbev.testeAmbev.dto.ProductDTO;

import java.util.Optional;

public interface ProductService {

    Optional<ProductDTO> findProduct(String productId);

    boolean supports(String productId);
}