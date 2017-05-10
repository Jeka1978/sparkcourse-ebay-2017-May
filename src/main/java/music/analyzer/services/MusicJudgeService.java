package music.analyzer.services;

import java.util.List;

/**
 * Created by Evegeny on 04/05/2017.
 */
public interface MusicJudgeService {
    public List<String> topXWords(String artist, int x);

    List<String> topXWordsWithDataset(String artist, int x);
}
