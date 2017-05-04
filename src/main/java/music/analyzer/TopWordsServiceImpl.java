package music.analyzer;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.broadcast.Broadcast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Service
public class TopWordsServiceImpl implements TopWordsService {

    @AutowiredBroadcast
    private Broadcast<UserProperties> userProperties;



    @Override
    public List<String> topXWords(JavaRDD<String> lines, int x) {
        JavaRDD<String> words = lines.map(String::toLowerCase)
                .flatMap(WordsUtil::getWords);
        words = words.filter(word -> !this.userProperties.value().garbage.contains(word));

        return words.mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum)
                .mapToPair(Tuple2::swap)
                .sortByKey(false)
                .map(Tuple2::_2)
                .take(x);
    }
}


















