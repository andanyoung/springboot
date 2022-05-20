package boot.andanyang.strategy.impl;

import boot.andanyang.strategy.Strategy;
import org.springframework.stereotype.Component;

/**
 * @author andanyang
 * @since 2022/1/20 17:31
 */
@Component("PLAN_C")
public class StrategyImplC implements Strategy {
    @Override
    public void process(Object o) {
        System.out.println("StrategyImplB");
    }
}
