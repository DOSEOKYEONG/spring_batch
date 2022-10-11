package com.ll.exam.spring_batch.app.order.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
public class RebateOrderItem extends BaseEntity {
    @OneToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private OrderItem orderItem;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProductOption productOption;

    private int quantity;
    // 가격
    private int price;  // 권장 판매가
    private int salePrice; // 실제 판매가
    private int wholesalePrice; // 도매가
    private int payPrice; // 결제금액
    private int refundPrice; // 환불금액
    private int refundQuantity; // 환불한 개수
    private int payFee; // 결제 수수료
    private boolean isPaid; // 결제 여부
    private LocalDateTime payDate; // 결제 날짜

    // 상품
    private String productName;

    // 상품 옵션
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "color", column = @Column(name = "product_option_color")),
            @AttributeOverride(name = "size", column = @Column(name = "product_option_size")),
            @AttributeOverride(name = "displayColor", column = @Column(name = "product_option_display_color")),
            @AttributeOverride(name = "displaySize", column = @Column(name = "product_option_display_size"))
    })
    private RebateOrderItem.EmbProductOption embProductOption;

    // 주문 품목
    private LocalDateTime orderItemCreateDate;

    public RebateOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;

        order = orderItem.getOrder();
        quantity = orderItem.getQuantity();
        productOption = orderItem.getProductOption();
        // 가격
        price = orderItem.getPrice();  // 권장 판매가
        salePrice = orderItem.getSalePrice(); // 실제 판매가
        wholesalePrice = orderItem.getWholesalePrice(); // 도매가
        payPrice = orderItem.getPayPrice(); // 결제금액
        refundPrice = orderItem.getRefundPrice(); // 환불금액
        refundQuantity = orderItem.getRefundQuantity(); // 환불한 개수
        payFee = orderItem.getPayFee(); // 결제 수수료
        isPaid = orderItem.isPaid(); // 결제 여부
        payDate = orderItem.getPayDate();

        // 상품 추가 데이터
        productName = orderItem.getProductOption().getProduct().getProductName();

        // 상품 옵션 추가데이터
        embProductOption = new EmbProductOption(orderItem.getProductOption());


        // 주문품목 추가데이터
        orderItemCreateDate = orderItem.getCreateDate();

    }

    @Embeddable
    @NoArgsConstructor
    public static class EmbProductOption {
        private String color;
        private String size;
        private String displayColor;
        private String displaySize;

        public EmbProductOption(ProductOption productOption) {
            color = productOption.getColor();
            size = productOption.getSize();
            displayColor = productOption.getDisplayColor();
            displaySize = productOption.getDisplaySize();
        }

    }
}
