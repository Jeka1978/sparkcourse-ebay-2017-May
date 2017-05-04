package music.analyzer;

import music.analyzer.conf.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Evegeny on 04/05/2017.
 */
public class Main {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(MainConfig.class);
    }
}
