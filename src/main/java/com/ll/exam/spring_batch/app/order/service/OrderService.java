package com.ll.exam.spring_batch.app.order.service;

import com.ll.exam.spring_batch.app.cart.entity.CartItem;
import com.ll.exam.spring_batch.app.cart.service.CartService;
import com.ll.exam.spring_batch.app.member.entity.Member;
import com.ll.exam.spring_batch.app.member.service.MemberService;
import com.ll.exam.spring_batch.app.order.entity.Order;
import com.ll.exam.spring_batch.app.order.entity.OrderItem;
import com.ll.exam.spring_batch.app.order.repository.OrderRepository;
import com.ll.exam.spring_batch.app.product.entity.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberService memberService;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createFromCart(Member member) {
        List<CartItem> cartItems = cartService.getItemsByMember(member);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            ProductOption productOption = cartItem.getProductOption();

            if (productOption.isOrderable(cartItem.getQuantity())) {
                orderItems.add(new OrderItem(productOption, cartItem.getQuantity()));

            }

            cartService.deleteItem(cartItem);
        }

        return create(member, orderItems);
    }

    @Transactional
    public Order create(Member member, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .member(member)
                .build();

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        orderRepository.save(order);

        return order;
    }

    public void payByRestCashOnly(Order order) {
        Member member = order.getMember();
        long restCash = member.getRestCash();

        int payPrice = order.calculatePayPrice();

        if (restCash < payPrice) {
            throw new RuntimeException("?????? ??????");
        }

        memberService.addCash(member, payPrice * -1, "????????????__???????????????");

        order.setPaymentDone(order);
        orderRepository.save(order);
    }

    @Transactional
    public void refund(Order order) {
        int payPrice = order.getPayPrice();
        memberService.addCash(order.getMember(), payPrice, "????????????__???????????????");

        order.setRefundDone();
        orderRepository.save(order);
    }
}
