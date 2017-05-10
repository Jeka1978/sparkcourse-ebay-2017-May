package music.analyzer.conf;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.storage.StorageLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import scala.Tuple2;

import java.util.Arrays;

import static music.analyzer.conf.ProfilesConst.DEV;
import static org.apache.spark.sql.functions.*;

/**
 * Created by Evegeny on 04/05/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class LinkedInTest {
    private static final String SALARY = "salary";
    private static final String AGE = "age";
    private static final String KEYWORDS = "keywords";
    private static final String KEYWORD = "keyword";
    private static final String AMOUNT = "amount";
    @Autowired
    private SQLContext sqlContext;

    @Test
    public void appropriativeDevloper() throws Exception {
        Dataset<Row> linkedIn = sqlContext.read().json("data/linkedIn/*json");
        linkedIn.persist(StorageLevel.MEMORY_AND_DISK());
        linkedIn.show();
        linkedIn.printSchema();
        Arrays.stream(linkedIn.schema().fields()).forEach(structField -> System.out.println(structField.dataType()));

        linkedIn = linkedIn.withColumn(SALARY, col(AGE).multiply(10).multiply(size(col(KEYWORDS))));
        linkedIn.persist(StorageLevel.MEMORY_AND_DISK());
        linkedIn.show();

//        linkedIn.select(explode(col(KEYWORDS))).as(KEYWORD))
        Dataset<Row> keywordDF = linkedIn.withColumn(KEYWORD, explode(col(KEYWORDS))).select(KEYWORD);
        keywordDF.show();

        String first = (String) keywordDF.toJavaRDD().map(row -> row.getAs(KEYWORD)).mapToPair(keyword -> new Tuple2<>(keyword, 1))
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey(false).map(Tuple2::_2).first();

        System.out.println("first = " + first);


        keywordDF = keywordDF.groupBy(KEYWORD).agg(count(col(KEYWORD)).as(AMOUNT)).orderBy(col(AMOUNT).desc());

        keywordDF.show();

        Row row = keywordDF.head();
        String mostPopular = row.getAs(KEYWORD);
        System.out.println("mostPopular = " + mostPopular);

        linkedIn.filter(array_contains(col(KEYWORDS),mostPopular)).filter(col(SALARY).leq(1200)).show();





    }

}








