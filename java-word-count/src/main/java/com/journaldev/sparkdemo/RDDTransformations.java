package com.journaldev.sparkdemo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class RDDTransformations {
	
    private static void transformations(String file1 , String file2) {
    	

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("JD Word Counter");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> rdd1 = sparkContext.textFile(file1);
        
        System.out.println("Rdd Memory Before: "+rdd1);
        rdd1 = rdd1.flatMap(s -> Arrays.asList(s.split(" ")).iterator());
        
        System.out.println("Rdd Memory After: "+rdd1);
        
        JavaRDD<String> rdd2 = sparkContext.textFile(file2);
        rdd2 = rdd2.flatMap(s -> Arrays.asList(s.split(" ")).iterator());
        
        JavaRDD result = rdd1.union(rdd2);
        
        result.saveAsTextFile("result");
        
    }

    public static void main(String[] args) {

        
        	System.out.println("Using Default File");
        	String file1 = "/home/brijesh/programming/personal/java-word-count/java-word-count/src/main/java/com/journaldev/sparkdemo/input2.txt";
        	String file2 = "/home/brijesh/programming/personal/java-word-count/java-word-count/src/main/java/com/journaldev/sparkdemo/input3.txt";
        	
        	transformations(file1 , file2);
       
    }
}