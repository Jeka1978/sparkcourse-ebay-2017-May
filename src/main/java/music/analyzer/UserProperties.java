package music.analyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Evegeny on 04/05/2017.
 */
@Component
public class UserProperties implements Serializable{

    public final Set<String> garbage;

    public UserProperties(@Value("${garbage}")String[] garbage) {
        this.garbage = new HashSet<>(Arrays.asList(garbage));
    }

}
