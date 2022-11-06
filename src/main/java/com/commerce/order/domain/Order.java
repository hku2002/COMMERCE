package com.commerce.order.domain;

import com.commerce.delivery.domain.Delivery;
import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.IEnumType;
import com.commerce.global.common.exception.BadRequestException;
import com.commerce.user.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.commerce.delivery.domain.Delivery.DeliveryStatus.STAND_BY;
import static com.commerce.order.domain.Order.OrderStatus.CANCELED;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@DynamicUpdate
@Table(name = "ORDERS")
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", updatable = false)
    private Member member;

    @OneToOne(mappedBy = "order", cascade = PERSIST)
    private Delivery delivery;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = PERSIST)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Builder
    public Order (Long id, Member member, Delivery delivery, List<OrderItem> orderItems, String name, int totalPrice) {
        this.id = id;
        this.member = member;
        this.orderItems = orderItems;
        this.delivery = delivery;
        this.name = name;
        this.status = OrderStatus.COMPLETED;
        this.totalPrice = totalPrice;
    }

    public enum OrderStatus implements IEnumType {
        COMPLETED   ("COMPLETED"),
        CANCELED    ("CANCELED");

        private final String value;
        OrderStatus(String value) { this.value = value; }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getName() {
            return value;
        }
    }

    /**
     * 주문 상태 변경
     * @param status 변경할 주문 상태값
     */
    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 주문 데이터 존재 체크
     * @param order 주문 객체
     */
    public void checkOrderExist(Order order) {
        if (ObjectUtils.isEmpty(order)) {
            throw new BadRequestException("주문이 존재하지 않습니다.");
        }
    }

    /**
     * 주문 취소 체크
     */
    public void checkOrderCanceled() {
        if (this.getStatus() == CANCELED) {
            throw new BadRequestException("이미 취소된 주문입니다.");
        }
    }

    /**
     * 배송 상태값 체크 및 주문 취소여부 확인
     * @param order 주문 객체
     */
    public void checkDeliveryCancelPossibility(Order order) {
        order.getDelivery().checkDeliveryExist(order.getDelivery());
        if (order.getDelivery().getStatus() != STAND_BY) {
            throw new BadRequestException("배송이 준비중인 상품만 주문 취소가 가능합니다.");
        }
    }

}
