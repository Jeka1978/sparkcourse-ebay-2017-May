package music.analyzer.prod;

import music.analyzer.conf.MainConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static music.analyzer.conf.ProfilesConst.DEV;
import static org.junit.Assert.*;

/**
 * Created by Evegeny on 08/05/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class ProdServiceTest {
    @Autowired
    private ProdService prodService;

    @Test
    public void start() throws Exception {
        prodService.start();
    }

}