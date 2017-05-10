package music.analyzer.prod;

import lombok.SneakyThrows;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sun.reflect.generics.repository.ClassRepository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Evegeny on 10/05/2017.
 */
@Component
// since 4.3 you don't need to implement this interface, you can just use @EventListner annotation
public class UdfRegistryApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SparkSession sparkSession;

    private Map<Class,DataType> types = new HashMap<>();

    public UdfRegistryApplicationListener() {
        types.put(String.class, DataTypes.StringType);
        types.put(Integer.class, DataTypes.IntegerType);
        types.put(Boolean.class, DataTypes.BooleanType);
        types.put(Double.class, DataTypes.DoubleType);

    }

   /* @EventListener   /// since spring 4.3
    public void registerUdf1(ContextRefreshedEvent event) {
        Collection<UDF1> udf1s = event.getApplicationContext().getBeansOfType(UDF1.class).values();
        for (UDF1 udf1 : udf1s) {
            Type type = ((ParameterizedType) udf1.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
            DataType dataType = types.get(type);
            sparkSession.udf().register(udf1.getClass().getName(), udf1, dataType);
        }
    }*/

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Collection<UDF1> udf1s = event.getApplicationContext().getBeansOfType(UDF1.class).values();
        for (UDF1 udf1 : udf1s) {
            Type type = ((ParameterizedType) udf1.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
            DataType dataType = types.get(type);
            sparkSession.udf().register(udf1.getClass().getName(), udf1, dataType);
        }
    }
}







