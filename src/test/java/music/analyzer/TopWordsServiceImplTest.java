package music.analyzer;

import music.analyzer.conf.MainConfig;
import music.analyzer.conf.ProfilesConst;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static music.analyzer.conf.ProfilesConst.DEV;
import static org.junit.Assert.*;

/**
 * Created by Evegeny on 04/05/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class TopWordsServiceImplTest {

    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private TopWordsService topWordsService;

    @Test
    public void topXWords() throws Exception {

        JavaRDD<String> rdd = sc.parallelize(Arrays.asList(
                "java is better than scala",
                "groovy is better than java",
                "python is the best",
                "java java java"
        ));
        List<String> list = topWordsService.topXWords(rdd, 1);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals("java",list.get(0));
    }

}