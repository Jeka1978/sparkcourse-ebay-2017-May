package taxi;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

/**
 * Created by Evegeny on 03/05/2017.
 */
public class Main {

    public static final String BOSTON = "boston";

    public static void main(String[] args) {

        //HADOOP_HOME = bin/winutils.exe

        SparkConf sparkConf = new SparkConf().setMaster("local[*]").setAppName("TAXI");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        JavaRDD<String> rdd = sc.textFile("data/taxi/trips.txt");
        rdd.persist(StorageLevel.MEMORY_AND_DISK());
        System.out.println("count = " + rdd.count());

        JavaRDD<TaxiOrder> taxiOrdersRdd = rdd.map(line -> {
            String[] arr = line.split(" ");
            return TaxiOrder.builder().id(arr[0]).city(arr[1].toLowerCase()).km(Integer.parseInt(arr[2])).build();
        });

        taxiOrdersRdd.persist(StorageLevel.MEMORY_AND_DISK());

        long numberOfLongBostonTrips = taxiOrdersRdd.filter(taxiOrder -> taxiOrder.getCity().equals(BOSTON))
                .filter(taxiOrder -> taxiOrder.getKm() > 10).count();
        System.out.println("numberOfLongBostonTrips = " + numberOfLongBostonTrips);

        Double totalBostonKm = taxiOrdersRdd.filter(taxiOrder -> taxiOrder.getCity().equals(BOSTON))
                .mapToDouble(TaxiOrder::getKm).sum();

        System.out.println("totalBostonKm = " + totalBostonKm);


        JavaPairRDD<String, String> driversDataRdd = sc.textFile("data/taxi/drivers.txt")
                .mapToPair(line -> {
                    String[] arr = line.split(",");
                    return new Tuple2<>(arr[0], arr[1]);
                });

        JavaPairRDD<String, Integer> totalDriverDistanceRdd = taxiOrdersRdd.mapToPair(taxiOrder -> new Tuple2<>(taxiOrder.getId(), taxiOrder.getKm()))
                .reduceByKey(Integer::sum);


        totalDriverDistanceRdd.join(driversDataRdd)
                .mapToPair(Tuple2::_2).sortByKey(false).take(3).forEach(System.out::println);


        Accumulator<Integer> accumulator = sc.accumulator(0, "small trips");

        taxiOrdersRdd.foreach(taxiOrder -> {
            if (taxiOrder.getKm() < 5) accumulator.add(1);
        });



        Integer smallTripsNumber = accumulator.value();
        System.out.println("smallTripsNumber = " + smallTripsNumber);

    }
}






















