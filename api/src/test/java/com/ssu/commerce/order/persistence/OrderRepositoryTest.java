package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.config.ObjectMapperConfig;
import com.ssu.commerce.order.supplier.OrderTestDataSupplier;
import com.ssu.commerce.core.jpa.JpaConfig;
import com.ssu.commerce.order.config.QuerydslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class OrderRepositoryTest implements OrderTestDataSupplier {

    @Test
    void testSelectOrderPage() {
    }
}
