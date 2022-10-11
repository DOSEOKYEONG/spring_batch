package com.ll.exam.spring_batch.app.order.entity;

import com.ll.exam.spring_batch.app.base.entity.BaseEntity;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
    private OrderItem orderItem;

    @ManyToOne(fetch = LAZY)
    @ToString.Exclude
    private Order order;

    @ManyToOne(fetch = LAZY)
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

    public RebateOrderItem(ProductOption productOption, int quantity) {
        this.productOption = productOption;
        this.quantity = quantity;
        this.price = productOption.getPrice();
        this.salePrice = productOption.getSalePrice();
        this.wholesalePrice = productOption.getWholesalePrice();
    }

    public int calculatePayPrice() {
        return salePrice * quantity;
    }

    public void setPaymentDone() {
        this.payFee = 0;
        this.payPrice = calculatePayPrice();
        this.isPaid = true;
    }

    public void setRefundDone() {
        if (refundQuantity == quantity) return;

        this.refundQuantity = quantity;
        this.refundPrice = payPrice;

    }

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
    }
}
