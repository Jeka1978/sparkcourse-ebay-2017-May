package music.analyzer.conf;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Configuration
@ComponentScan(basePackages = "music.analyzer")
@PropertySource("classpath:user.properties")
//env. variable:   spring.profiles.active = PROD
public class MainConfig {

    @Autowired
    private SparkConf sparkConf;

    @Autowired
    public MainConfig(Environment environment) {
        if (environment.getActiveProfiles().length==0) {
            throw new IllegalStateException("you should activate some spring profile. spark-submit --conf \"spark.driver.extraJavaOptions=-Dspring.active.profiles=...\" ...");
        }
    }



    @Bean
    public JavaSparkContext sc() {
        return new JavaSparkContext(sparkConf);
    }

    @Bean
    public SparkSession sparkSession(){
        return new SparkSession(sc().sc());
    }

    @Bean
    public SQLContext sqlContext() {
        return new SQLContext(sc());
    }

   /* @Bean you need this bean if you spring version < 4.3
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}
















