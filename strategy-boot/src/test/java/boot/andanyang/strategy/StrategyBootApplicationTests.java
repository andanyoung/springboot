package boot.andanyang.strategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StrategyBootApplicationTests {

    @Autowired
    StrategyContext strategyContext;

    @Test
    void contextLoads() {

        Strategy strategy = strategyContext.getStrategy("PLAN_A");
        strategy.process(1L);


        strategy = strategyContext.getStrategy("PLAN_E");
        strategy.process(2L);
    }
}
