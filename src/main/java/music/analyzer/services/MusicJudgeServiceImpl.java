package music.analyzer.services;

import music.analyzer.TopWordsService;
import music.analyzer.WordsUtil;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Service
public class MusicJudgeServiceImpl implements MusicJudgeService {
    @Autowired
    private JavaSparkContext sc;

    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private TopWordsService topWordsService;

    @Override
    public List<String> topXWords(String artist, int x) {
        JavaRDD<String> rdd = sc.textFile("data/songs/" + artist + "/*.txt");

        return topWordsService.topXWords(rdd, x);
    }

    @Override
    public List<String> topXWordsWithDataset(String artist, int x) {
        Dataset<String> dataset = sqlContext.read().text("data/songs/" + artist + "/*.txt").as(Encoders.STRING());
//                Dataset<Row> rowDataset = dataset.flatMap(WordsUtil::getWords, Encoders.STRING()).withColumnRenamed("value", "word").toDF();
        Dataset<Row> rowDataset = dataset.flatMap(WordsUtil::getWords, Encoders.STRING()).toDF("word");  // this one is shorter but can be used only for dataframe with one column
        rowDataset = rowDataset.groupBy("word").agg(count(col("word")).as("amount")).orderBy(col("amount").desc());
        rowDataset.show();
        List<Row> word = rowDataset.select("word").takeAsList(x);
        int index = Arrays.asList(rowDataset.columns()).indexOf("word");
//        List<String> list = word.stream().map(row -> row.getString(index)).collect(Collectors.toList());
        List<String> list = rowDataset.select("word").as(Encoders.STRING()).takeAsList(x);  //this ine is faster because we are not taking rows to driver, but only strings
        return list;
    }

}


















