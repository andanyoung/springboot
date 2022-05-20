package boot.andanyang.strategy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author andanyang
 * @since 2022/1/20 17:44
 */
@Component
public class StrategyContext implements InitializingBean, ApplicationContextAware {

    private static Strategy DEFAULT_STRATEGY = new DefaultStrategy();
    private ApplicationContext applicationContext;
    @Resource
    private Map<String, Strategy> STRATEGY_MAP = new HashMap<>(4);

    @Override
    public void afterPropertiesSet() throws Exception {

        STRATEGY_MAP = applicationContext.getBeansOfType(Strategy.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public Strategy getStrategy(String strategyName) {
        return STRATEGY_MAP.get(strategyName) == null ? DEFAULT_STRATEGY : STRATEGY_MAP.get(strategyName);
    }

    static class DefaultStrategy implements Strategy {
        @Override
        public void process(Object o) {
            System.out.println("default PLAN");
        }
    }
}
