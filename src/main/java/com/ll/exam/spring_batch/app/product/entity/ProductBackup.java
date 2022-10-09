package com.ll.exam.spring_batch.app.product.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class ProductBackup extends BaseEntity {

    private String productName;

    private int price; // 소비자가격

    private int salePrice; //

    private String makerShopName;

    private int wholesalePrice; // 도매가격

    private boolean isSoldOut; // 관련 옵션들이 전부 판매불능 상태일 때

    @OneToOne(fetch = LAZY)
    private Product product;

    public ProductBackup(Product product) {
        this.product = product;
        salePrice = product.getSalePrice();
        price = product.getPrice();
        wholesalePrice = product.getWholesalePrice();
        productName = product.getProductName();
        makerShopName = product.getMakerShopName();
        isSoldOut = product.isSoldOut();
    }
}