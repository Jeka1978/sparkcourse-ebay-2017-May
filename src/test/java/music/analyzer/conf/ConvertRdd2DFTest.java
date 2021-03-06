package music.analyzer.conf;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static music.analyzer.conf.ProfilesConst.DEV;

/**
 * Created by Evegeny on 04/05/2017.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainConfig.class)
@ActiveProfiles(DEV)
public class ConvertRdd2DFTest {

    @Autowired
    private SQLContext sqlContext;

    @Autowired
    private JavaSparkContext sc;

    @Test
    public void test() throws Exception {
        JavaRDD<String> rdd = sc.textFile("data/taxi/trips.txt");

        JavaRDD<Row> rowJavaRDD = rdd.map(line -> {
            String[] split = line.split(" ");
            return RowFactory.create(split[0], split[1], Integer.parseInt(split[2]));
        });

        StructType schema = DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("id", DataTypes.StringType, false),
                DataTypes.createStructField("city", DataTypes.StringType, false),
                DataTypes.createStructField("km", DataTypes.IntegerType, false)});

        Dataset<Row> dataFrame = sqlContext.createDataFrame(rowJavaRDD, schema);

        dataFrame.show();


    }
}
















