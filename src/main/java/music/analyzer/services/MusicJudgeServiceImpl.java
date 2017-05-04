package music.analyzer.services;

import music.analyzer.TopWordsService;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Service
public class MusicJudgeServiceImpl implements MusicJudgeService {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private TopWordsService topWordsService;

    @Override
    public List<String> topXWords(String artist, int x) {
        JavaRDD<String> rdd = sc.textFile("data/songs/" + artist + "/*.txt");

        return topWordsService.topXWords(rdd, x);
    }
}


















