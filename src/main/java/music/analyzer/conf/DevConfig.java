package music.analyzer.conf;

import org.apache.spark.SparkConf;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static music.analyzer.conf.ProfilesConst.DEV;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Configuration
@Profile(DEV)
public class DevConfig {
    @Bean
    public SparkConf sparkConf() {
        return new SparkConf().setAppName("Words").setMaster("local[*]");
    }
}
