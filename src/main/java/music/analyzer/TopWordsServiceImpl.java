package music.analyzer;

import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Evegeny on 04/05/2017.
 */
public class TopWordsServiceImpl implements TopWordsService {
    @Override
    public List<String> topXWords(JavaRDD<String> lines, int x) {
        JavaRDD<String> words = lines.map(String::toLowerCase)
                .flatMap(WordsUtil::getWords);

        return words.mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey(false)
                .map(Tuple2::_2)
                .take(x);
    }
}


















