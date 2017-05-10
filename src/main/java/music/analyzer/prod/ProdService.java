package music.analyzer.prod;

import music.analyzer.conf.MainConfig;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.*;

/**
 * Created by Evegeny on 08/05/2017.
 */
@Service
public class ProdService {
    @Autowired
    private SQLContext sqlContext;

    @Value("${pathToWrite}")
    private String pathToWrite;

    @Value("${pathToRead}")
    private String pathToRead;

    public void start(){

    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(MainConfig.class).getBean(ProdService.class).start();
    }


}
