package music.analyzer.conf;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static music.analyzer.conf.ProfilesConst.PROD;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Configuration
@Profile(PROD)
public class ProdConfig {
    @Bean
    public SparkConf sparkConf() {
        return new SparkConf().setAppName("Words").setMaster("local[*]");
    }
}
