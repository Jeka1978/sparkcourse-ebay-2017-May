package music.analyzer;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Evegeny on 04/05/2017.
 */
public class TopWordsServiceImplTest {
    @Test
    public void topXWords() throws Exception {
        SparkConf sparkConf = new SparkConf().setAppName("no matter").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> rdd = sc.parallelize(Arrays.asList(
                "java is better than scala",
                "groovy is better than java",
                "python is the best",
                "java java java"
        ));
        TopWordsServiceImpl topWordsService = new TopWordsServiceImpl();
        List<String> list = topWordsService.topXWords(rdd, 1);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals("java",list.get(0));
    }

}