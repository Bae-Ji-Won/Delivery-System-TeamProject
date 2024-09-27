package com.webest.order.domain.model;


import com.webest.app.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long storeId;

    private Long paymentId;

    private Long couponId;

    private Long userId;

    @Enumerated(EnumType.STRING)  // Enum을 문자열로 DB에 저장
    private OrderStatus orderStatus;

    private Boolean isRequest;

    private String requests;

    private Integer totalQuantity;

    private Double totalProductPrice;

    private Double couponAppliedAmount;

    private Double deliveryTipAmount;

    private Double totalPaymentPrice;

    public static Order create(Long storeId,
                               Long paymentId,
                               Long couponId,
                               Long userId,
                               OrderStatus orderStatus,
                               Boolean isRequest,
                               Integer totalQuantity,
                               Double totalProductPrice,
                               Double couponAppliedAmount,
                               Double deliveryTipAmount,
                               Double totalPaymentPrice)
    {
        Order order = new Order();
        order.storeId = storeId;
        order.paymentId = paymentId;
        order.couponId = couponId;
        order.userId = userId;
        order.orderStatus = orderStatus;
        order.isRequest = isRequest;
        order.totalQuantity = totalQuantity;
        order.totalProductPrice = totalProductPrice;
        order.couponAppliedAmount = couponAppliedAmount;
        order.deliveryTipAmount = deliveryTipAmount;
        order.totalPaymentPrice = totalPaymentPrice;

        return order;
    }

    public void update(Long storeId,
                       Long paymentId,
                       Long couponId,
                       Long userId,
                       OrderStatus orderStatus,
                       Boolean isRequest,
                       Integer totalQuantity,
                       Double totalProductPrice,
                       Double couponAppliedAmount,
                       Double deliveryTipAmount,
                       Double totalPaymentPrice) {
        this.storeId = storeId;
        this.paymentId = paymentId;
        this.couponId = couponId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.isRequest = isRequest;
        this.totalQuantity = totalQuantity;
        this.totalProductPrice = totalProductPrice;
        this.couponAppliedAmount = couponAppliedAmount;
        this.deliveryTipAmount = deliveryTipAmount;
        this.totalPaymentPrice = totalPaymentPrice;
    }

    public void delete(Boolean is_delete) {
        this.isDeleted = true;
    }

}
