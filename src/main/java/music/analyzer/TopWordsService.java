package music.analyzer;

import org.apache.spark.api.java.JavaRDD;

import java.util.List;

/**
 * Created by Evegeny on 04/05/2017.
 */
public interface TopWordsService {
    List<String> topXWords(JavaRDD<String> rdd, int x);
}
