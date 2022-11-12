package com.commerce.order.domain;

import com.commerce.global.common.config.TestJPAQueryFactoryConfig;
import com.commerce.user.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static com.commerce.order.domain.Order.OrderStatus.COMPLETED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@Import(TestJPAQueryFactoryConfig.class)
@AutoConfigureTestDatabase(replace = NONE)
class OrderTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("updateOrderStatus 메소드로 주문상태값을 COMPLETED 로 변경하면 COMPLETED 로 변경된다.")
    void updateOrderStatusCompletedChangedTest() {
        // given
        Member member = Member.builder()
                .id(1L)
                .userId("test01")
                .build();
        Order order = this.testEntityManager.persistAndFlush(Order.builder()
                .name("test product")
                .totalPrice(10000)
                .member(member)
                .build());

        // when
        order.updateOrderStatus(COMPLETED);

        // then
        assertThat(order.getStatus()).isEqualTo(COMPLETED);
    }
}