package com.shop.display.service.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.display.domain.Display;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDER_DETAIL")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", updatable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "display_id", referencedColumnName = "id", updatable = false)
    private Display display;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public OrderDetail (Long id, Order order, Display display) {
        this.id = id;
        this.order = order;
        this.display = display;
        this.createdAt = LocalDateTime.now();
    }

}
