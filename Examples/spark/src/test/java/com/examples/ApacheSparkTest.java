package com.examples;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;


// Apache Spark's Web UI is available on http://localhost:4040 when the application is running
public class ApacheSparkTest {
    @Test
    void testApacheSpark() throws IOException {
        Instant start = Instant.now();

        String amazonDatasetFile = "../../../kcore_5.json"; // You need to download the file yourself from http://jmcauley.ucsd.edu/data/amazon/links.html and place it one directory above the Git repository
        SparkSession spark = SparkSession.builder().appName("Amazon Dataset Example").master("local[*]").getOrCreate();

        assertEquals(29, Files.size(Path.of(amazonDatasetFile)) / 1024 / 1024 / 1024 );
        System.out.println("29GB Dataset contains 41.135.700 records");

        Encoder<Review> reviewEncoder = Encoders.bean(Review.class);

        Dataset<Review> amazonDataSet = spark.read().option("multiLine", false).json(amazonDatasetFile).select("reviewText", "reviewerID").as(reviewEncoder);
        amazonDataSet.printSchema();

        Dataset<Review> filteredSet = amazonDataSet.filter((FilterFunction<Review>) a -> a.getReviewText().contains("Java Programming Language"));

        assertEquals(28, filteredSet.count());

        filteredSet.show((int) filteredSet.count(), false);

        Instant finish = Instant.now();
        Duration duration = Duration.between(start, finish);
        System.out.println("Spark duration: " + duration.toSeconds());
        spark.stop();
    }
}
