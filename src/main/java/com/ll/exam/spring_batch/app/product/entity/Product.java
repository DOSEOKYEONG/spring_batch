package com.ll.exam.spring_batch.app.product.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class Product extends BaseEntity {

    private String productName;

    private int price; // 소비자가격

    private int salePrice; //

    private String makerShopName;

    private int wholesalePrice; // 도매가격

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ProductOption> productOptions = new ArrayList<>();

    public void addOption(ProductOption productOption) {
        productOption.setProduct(this);
        productOption.setPrice(getPrice());
        productOption.setWholesalePrice(getWholesalePrice());
        productOption.setSalePrice(getSalePrice());

        productOptions.add(productOption);
    }
}