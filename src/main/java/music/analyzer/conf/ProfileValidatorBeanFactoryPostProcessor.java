package music.analyzer.conf;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by Evegeny on 04/05/2017.
 */
//@Component
public class ProfileValidatorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment environment = beanFactory.getBean(ConfigurableEnvironment.class);
        environment.addActiveProfile("PROD");
        if (environment.getActiveProfiles().length==0) {
            throw new IllegalStateException("you should activate some spring profile. spark-submit --conf \"spark.driver.extraJavaOptions=-Dspring.active.profiles=...\" ...");
        }
    }
}
