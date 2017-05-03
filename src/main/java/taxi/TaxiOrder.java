package taxi;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Evegeny on 03/05/2017.
 */
@Builder
@Data
public class TaxiOrder {
    private String id;
    private int km;
    private String city;
}
