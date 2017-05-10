package music.analyzer;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Component
public class UserProperties implements Serializable{

    public final Set<String> garbage;
    @Getter
    private Map<Long, String> countryMap = new HashMap<>();

    public UserProperties(@Value("${garbage}")String[] garbage) {
        this.garbage = new HashSet<>(Arrays.asList(garbage));
    }

    @Value("${countries}")
    private void buildCountryMap(String line) {
        String[] arr = line.split(";");
        for (String pair : arr) {
            String[] split = pair.split("=");
            if(split.length!=2) throw new IllegalStateException("you are an idiot");
            countryMap.put(Long.valueOf(split[0]), split[1]);
        }
    }

}




















