package com.test.sparkdemo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class WordCounter {
	
    private static void wordCount(String fileName) {
    	

        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("JD Word Counter");

        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        JavaRDD<String> wordsFromFileUpper = inputFile.map(s->s.toUpperCase());
        JavaRDD<String> wordsFromFile = wordsFromFileUpper.flatMap(content -> Arrays.asList(content.split(" ")).iterator());
      
        JavaPairRDD countData = wordsFromFile.mapToPair(t -> new Tuple2(t, 1)).reduceByKey((x, y) -> (int) x + (int) y).mapValues(x -> (int)x* (int)x);

        
        JavaRDD<String> sortedKeys = countData.sortByKey(true).keys();
        
        sortedKeys.saveAsTextFile("SortedKeys");
        
        countData.saveAsTextFile("CountData");
    }

    public static void main(String[] args) {

        if (args.length != 0) {
            System.out.println("Use File Provided");
            wordCount(args[0]);
            
        }else {
        	System.out.println("Using Default File");
        	String file = "/home/brijesh/programming/personal/java-word-count/java-word-count/src/main/java/com/journaldev/sparkdemo/input.txt";
        	wordCount(file);
        }
    }
}