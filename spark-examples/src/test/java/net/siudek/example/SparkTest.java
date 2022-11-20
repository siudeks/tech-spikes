package net.siudek.example;

import java.util.Arrays;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import scala.Tuple2;

class SparkTest {

    SparkSession newContext() {
        return SparkSession.builder()
                .master("local[1]")
                .appName("SparkByExample")
                .getOrCreate();
    }

    @Test
    public void testApp() {
        var sc = JavaSparkContext.fromSparkContext(newContext().sparkContext());
        var f = sc.textFile("src/test/resources/dict1.txt");
        FlatMapFunction<String, String> mapper = s -> Arrays.asList(s.split(" ")).iterator();
        var counts = f.flatMap(mapper)
            .mapToPair(it -> new Tuple2<>(it, 1))
            .reduceByKey((v1, v2) -> v1 + v2)
            .count();
        Assertions.assertThat(counts).isEqualTo(6);
    }

}
