package music.analyzer;

import music.analyzer.conf.MainConfig;
import music.analyzer.services.MusicJudgeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Created by Evegeny on 04/05/2017.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        MusicJudgeService judgeService = context.getBean(MusicJudgeService.class);
        List<String> topXWords = judgeService.topXWords("beatles", 3);
        System.out.println("topXWords = " + topXWords);
    }
}
