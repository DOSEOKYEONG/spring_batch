package com.ll.exam.spring_batch.app.product.service;

import com.ll.exam.spring_batch.app.product.entity.Product;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import com.ll.exam.spring_batch.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(String productName, int salePrice, int wholesalePrice,
                          String makerShopName, List<ProductOption> productOptionList) {
        int price = (int)Math.ceil(wholesalePrice * 1.6) / 100 * 100;

        Product product = Product.builder()
                .productName(productName)
                .salePrice(salePrice)
                .price(price)
                .wholesalePrice(wholesalePrice)
                .makerShopName(makerShopName)
                .build();

//        productRepository.save(product);

        for ( ProductOption option : productOptionList ) {
            product.addOption(option);
        }

        productRepository.save(product);

        return product;
    }
}
