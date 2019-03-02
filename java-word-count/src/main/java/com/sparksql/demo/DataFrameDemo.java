package com.sparksql.demo;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataFrameDemo {

	public static void main(String[] args) {
		SparkSession session = SparkSession.builder().master("local").appName("jsonreader").getOrCreate();
		Dataset<Row> df = session.read().option("multiLine", true).json("/home/brijesh/programming/personal/java-word-count/java-word-count/data.json");
		df.show(false);
		
		System.out.println("Alias ....");
		
		 df = df.withColumnRenamed(df.columns()[df.columns().length - 1], "income");
			  
			df = df.select("age","city").withColumnRenamed("city", "pity");
			df.show(false);
		
		
		

	}

}
